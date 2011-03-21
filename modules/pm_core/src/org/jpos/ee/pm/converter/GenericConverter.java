/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
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

/**
 * A generic converter that uses a beanbash based xml for excecution.
 * 
 * @author jpaoletti
 */
public class GenericConverter extends Converter {

    private String filename;
    private Interpreter bsh;
    private String visualize;
    private String build;

    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        try {
            Interpreter bash = getBsh();
            EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
            Field field = (Field) ctx.get(PM_FIELD);
            Object o = getValue(einstance, field);
            bash.set("value", o);
            bash.set("converter", this);
            debug("Generic Converter Visualize value: " + o);
            if (o == null) {
                return getConfig("null-value", "-");
            }
            String result = bash.eval(visualize).toString();
            final String res = visualize(result, ctx.getString(PM_EXTRA_DATA));
            if ("IgnoreConvertionException".equals(res)) {
                throw new IgnoreConvertionException("");
            }
            final Converter c = field.getDefaultConverter();
            if (c != null) {
                ctx.put(PM_FIELD_VALUE, res);
                return (String) c.visualize(ctx);
            } else {
                return res;
            }
        } catch (EvalError e) {
            getLog().error("BSH Interpreter Evaluation", e);
        }
        return null;
    }

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        try {
            Interpreter bash = getBsh();
            bash.set("value", ctx.get(PM_FIELD_VALUE));
            bash.set("converter", this);
            final Object res = bash.eval(build);
            if ("IgnoreConvertionException".equals(res)) {
                throw new IgnoreConvertionException("");
            }
            return res;
        } catch (EvalError e) {
            getLog().error("BSH Interpreter Evaluation Error", e);
        }
        return null;
    }

    /**
     *
     */
    public GenericConverter() {
        super();
    }

    private Interpreter getBsh() {
        if (bsh == null) {
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
     * @throws ConverterException
     */
    public void readFile(String filename) throws ConverterException {
        try {
            createXMLReader().parse(filename);
        } catch (Exception e) {
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
     * @throws ConverterException
     */
    public void readFile(InputStream input) throws ConverterException {
        try {
            createXMLReader().parse(new InputSource(input));
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    private XMLReader createXMLReader() throws SAXException {
        XMLReader reader = null;
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            reader = XMLReaderFactory.createXMLReader(
                    System.getProperty(
                    "org.xml.sax.driver",
                    "org.apache.crimson.parser.XMLReaderImpl"));
        }
        //reader.setFeature ("http://xml.org/sax/features/validation", true);
        GenericContentHandler handler = new GenericContentHandler();
        reader.setContentHandler(handler);
        reader.setErrorHandler(handler);
        return reader;
    }

    /**
     *
     * @return a descriptive string
     */
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        if (filename != null) {
            sb.append('[');
            sb.append(filename);
            sb.append(']');
        }
        return sb.toString();
    }

    private Interpreter initBSH() throws UtilEvalError, EvalError {
        Interpreter bash = new Interpreter();
        BshClassManager bcm = bash.getClassManager();
        bcm.setClassPath(getPresentationManager().getService().getServer().getLoader().getURLs());
        bcm.setClassLoader(getPresentationManager().getService().getServer().getLoader());
        bash.set("qbean", this);
        return bash;
    }

    /**
     * 
     */
    public class GenericContentHandler extends DefaultHandler {

        private String value;

        /**
         * 
         */
        @Override
        public void startDocument() {
        }

        /**
         * 
         * @throws SAXException
         */
        @Override
        public void endDocument() throws SAXException {
            if (visualize == null || build == null) {
                throw new SAXException("Format error in XML Field Description File");
            }
        }

        /**
         *
         * @param ch
         * @param start
         * @param length
         * @throws SAXException
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            value = new String(ch);
        }

        /**
         *
         * @param namespaceURI
         * @param localName
         * @param qName
         * @param atts
         * @throws SAXException
         */
        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        }

        /**
         * 
         * @param namespaceURI
         * @param localName
         * @param qName
         */
        @Override
        public void endElement(String namespaceURI, String localName, String qName) {
            if (localName.compareTo("visualize") == 0) {
                visualize = value;
            }
            if (localName.compareTo("build") == 0) {
                build = value;
            }
        }

        // ErrorHandler Methods
        /**
         *
         * @param ex
         * @throws SAXException
         */
        @Override
        public void error(SAXParseException ex) throws SAXException {
            throw ex;
        }

        /**
         * 
         * @param ex
         * @throws SAXException
         */
        @Override
        public void fatalError(SAXParseException ex) throws SAXException {
            throw ex;
        }
    }
}
