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
package org.jpos.ee.pm.menu;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.util.LogEvent;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**A parser class for the pm.locations.xml configuration file.
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 */
public class MenuItemLocationsParser extends DefaultHandler{
    private static final String TAB = "    ";
    private String conf;
    private Map<String,MenuItemLocation> locations;
    private LogEvent evt;
    private boolean error = false;
    
    public MenuItemLocationsParser (LogEvent evt, String conf) {
        this.setConf(conf);
        this.evt = evt;
        init();
    }
    
    private void init() {
        locations = new HashMap<String, MenuItemLocation>();
        evt.addMessage(TAB+"Locations");
        error = false;
        parseConfig();        
    }
    
    private void parseConfig() {
        try {
            SAXParserFactory dbf = SAXParserFactory.newInstance();
            SAXParser db = dbf.newSAXParser();
            db.parse(conf,this);
        }catch(Exception e) {
            PMLogger.error(e);
        }
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.compareTo("location") == 0){
            String id = attributes.getValue("id");
            String clazz = attributes.getValue("class");
            try {
                locations.put(id, (MenuItemLocation) EntitySupport.newObjectOf(clazz));
                evt.addMessage(TAB+TAB+String.format("[%s] %s", id,clazz));
            } catch (Exception e) {
                evt.addMessage(TAB+TAB+"Error loading location: "+id+" ["+clazz+"]");
                error = true;
            }
        }
    }
    

    /**
     * @param conf the conf to set
     */
    public void setConf(String conf) {
        this.conf = conf;
    }
    /**
     * @return the conf
     */
    public String getConf() {
        return conf;
    }

    /**
     * @return the locations
     */
    public Map<String,MenuItemLocation> getLocations() {
        return locations;
    }

    public boolean hasError() {
        return error;
    }
}