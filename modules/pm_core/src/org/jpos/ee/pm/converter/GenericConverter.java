/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.converter;

import java.io.InputStream;

import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import bsh.BshClassManager;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.UtilEvalError;


public class GenericConverter extends Converter{
    private String filename;
    private Interpreter bsh;
    private String visualize;
    private String build;

    
    public String visualize(PMContext ctx) throws ConverterException {
        try {
            Interpreter bsh = getBsh();
            EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
            Field field = (Field) ctx.get(PM_FIELD);
            Object o = getValue(einstance, field);
            bsh.set("value", o);
            bsh.set("converter", this);
            debug("Generic Converter Visualize value: "+o);
            if(o==null) return getConfig("null-value","-");
            String result = bsh.eval (visualize).toString();
            return visualize(result, ctx.getString(PM_EXTRA_DATA));
        } catch (EvalError e) {
            getLog().error("BSH Interpreter Evaluation", e);
        }
        return null;
    }

    
    public Object build(PMContext ctx) throws ConverterException {
        try {
            Interpreter bsh = getBsh();
            bsh.set("value", ctx.get(PM_FIELD_VALUE));
            return bsh.eval (build);
        } catch (EvalError e) {
            getLog().error("BSH Interpreter Evaluation", e);
        }
        return null;
    }
    

    public GenericConverter() {
        super();
    }

    private Interpreter getBsh() {
        if(bsh == null){
            try {
                this.filename = getConfig("filename");
                readFile(filename);
                bsh = initBSH();
            } catch (Exception e) {
                getLog().error("BSH Interpreter Creation", e);
            }
        }
        return bsh;
    }
    
    /**
     * Parse the field descriptions from an XML file.
     *
     * <pre>
     * Uses the sax parser specified by the system property 'sax.parser'
     * The default parser is org.apache.crimson.parser.XMLReaderImpl
     * </pre>
     * @param filename The XML field description file
     */
    public void readFile(String filename) throws ConverterException {
        try {
            createXMLReader().parse(filename);  
        }catch (Exception e) {
            throw new ConverterException(e);
        }
    }
    
    /**
     * Parse the field descriptions from an XML InputStream.
     *
     * <pre>
     * Uses the sax parser specified by the system property 'sax.parser'
     * The default parser is org.apache.crimson.parser.XMLReaderImpl
     * </pre>
     * @param input The XML field description InputStream
     */
    public void readFile(InputStream input) throws ConverterException {
        try {
            createXMLReader().parse(new InputSource(input));
        }catch (Exception e) {
            throw new ConverterException(e);
        }
    }
        
    private XMLReader createXMLReader () throws SAXException {
        XMLReader reader = null;
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            reader = XMLReaderFactory.createXMLReader (
                System.getProperty( 
                    "org.xml.sax.driver", 
                    "org.apache.crimson.parser.XMLReaderImpl"
                )
            );
        }
        //reader.setFeature ("http://xml.org/sax/features/validation", true);
        GenericContentHandler handler = new GenericContentHandler();
        reader.setContentHandler(handler);
        reader.setErrorHandler(handler);
        return reader;
    }
    
    public String getDescription () {
        StringBuilder sb = new StringBuilder();
        sb.append (getClass().getName());
        if (filename != null) {
            sb.append ('[');
            sb.append (filename);
            sb.append (']');
        }
        return sb.toString();
    }
    
    private Interpreter initBSH () throws UtilEvalError, EvalError {
        Interpreter bsh = new Interpreter ();
        BshClassManager bcm = bsh.getClassManager();
        bcm.setClassPath(getService().getServer().getLoader().getURLs());
        bcm.setClassLoader(getService().getServer().getLoader());
        bsh.set  ("qbean", this);
        return bsh;
    }

    public class GenericContentHandler extends DefaultHandler{
        private String value;
        public void startDocument(){
            
        }

        public void endDocument() throws SAXException{
            if (visualize == null || build == null) {
                throw new SAXException ("Format error in XML Field Description File");
            }
        }
        
        public void characters(char[] ch, int start, int length) throws SAXException {
            value = new String(ch);
        }

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
            
        }
        
        public void endElement(String namespaceURI, String localName, String qName){
                if(localName.compareTo("visualize")==0)
                    visualize = value;
                if(localName.compareTo("build")==0)
                    build = value;        
        }

        // ErrorHandler Methods
        public void error (SAXParseException ex) throws SAXException {
            throw ex;
        }

        public void fatalError (SAXParseException ex) throws SAXException{
            throw ex;
        }
    }
}
