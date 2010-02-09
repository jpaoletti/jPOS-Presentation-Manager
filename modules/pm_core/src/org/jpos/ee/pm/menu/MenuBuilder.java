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

package org.jpos.ee.pm.menu;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.core.PMService;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**A parser class to parse the pm.menu.xml configuration file. 
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class MenuBuilder extends DefaultHandler{
    public  MenuList menu;
    public  MenuItem item;
    private String conf;
    private String value;
    private PMService service;

    /**The constructor of the parser. It needs the configuration file name and the PMService. 
     * @param conf The relative filename of the pm.menu.xml file
     * @param service The PMService*/
    public MenuBuilder(String conf, PMService service) {
        this.conf = conf;
        this.service = service;
        initMenu();
    }

    private void initMenu() {
        menu = parseConfig(conf);
    }

    private MenuList parseConfig(String conf) {
        SAXParserFactory dbf = SAXParserFactory.newInstance();
		try {
            SAXParser db = dbf.newSAXParser();
            db.parse(conf,this);
            return menu;
		}catch(Exception e) {
			PMLogger.error(e);
		}
        return null;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    	value = new String(ch, start, length);
    }

    public void startDocument() throws SAXException {
        menu = new MenuList();
        menu.setService(service);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	String s = attributes.getValue("text");
    	String s2 = attributes.getValue("perm");
	    if(qName.compareTo("menu-list") == 0) processList(s,s2); else
	    if(qName.compareTo("menu-item") == 0) processItem(s,s2);
	    if(qName.compareTo("path") == 0)      value = null;
	    if(qName.compareTo("external") == 0)  value = null;
	    if(qName.compareTo("location") == 0){       
	    	value = null;
	    	item.parseLocation(attributes.getValue("id"), attributes.getValue("value"));
	    }
	    if(qName.compareTo("embed") == 0)     value = null;
    }
  
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	if(qName.compareTo("menu-list") == 0) if(menu.getParent()!= null) menu = menu.getParent();
	    if(qName.compareTo("external") == 0)  item.setExternal(value.compareTo("true")==0);
	    if(qName.compareTo("embed") == 0)  	  item.setEmbed(value.compareTo("true")==0);
    }

    private void processItem(String text, String perm) {
        MenuItem m = new MenuItem(text, "");
        m.setService(service);
        m.setPermission(perm);
        item = m;
        menu.add(m);
    }

    private void processList(String name, String perm) {
        MenuList subm = new MenuList();
        subm.setService(service);
        subm.setText(name);
        subm.setPermission(perm);
        menu.add(subm);
        menu = subm;
    }
}