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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jpos.ee.pm.core.PresentationManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**A parser class to parse the pm.menu.xml configuration file. 
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class MenuBuilder extends DefaultHandler {

    private MenuList menu;
    private MenuItem item;
    private String conf;

    /**The constructor of the parser. It needs the configuration file name and the PMService. 
     * @param conf The relative filename of the pm.menu.xml file
     **/
    public MenuBuilder(String conf) {
        this.conf = conf;
        initMenu();
    }

    private void initMenu() {
        menu = parseConfig(conf);
    }

    private MenuList parseConfig(String conf) {
        SAXParserFactory dbf = SAXParserFactory.newInstance();
        try {
            SAXParser db = dbf.newSAXParser();
            db.parse(conf, this);
            return menu;
        } catch (Exception e) {
            PresentationManager.pm.error(e);
        }
        return null;
    }

    /**
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        menu = new MenuList();
    }

    /**
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String s = attributes.getValue("text");
        String s2 = attributes.getValue("perm");
        if (qName.compareTo("menu-list") == 0) {
            processList(s, s2);
        } else if (qName.compareTo("menu-item") == 0) {
            processItem(s, s2);
        }
        if (qName.compareTo("location") == 0) {
            item.parseLocation(attributes.getValue("id"), attributes.getValue("value"));
        }
    }

    /**
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.compareTo("menu-list") == 0) {
            if (menu.getParent() != null) {
                menu = menu.getParent();
            }
        }
    }

    private void processItem(String text, String perm) {
        MenuItem m = new MenuItem(text);
        m.setPermission(perm);
        item = m;
        menu.add(m);
    }

    private void processList(String name, String perm) {
        MenuList subm = new MenuList();
        subm.setText(name);
        subm.setPermission(perm);
        menu.add(subm);
        menu = subm;
    }

    /**
     * Getter for menu
     * @return The MenuList
     */
    public MenuList getMenu() {
        return menu;
    }

    /**
     * Setter for menu
     * @param menu
     */
    public void setMenu(MenuList menu) {
        this.menu = menu;
    }
}
