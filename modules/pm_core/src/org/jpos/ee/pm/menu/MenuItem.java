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

import org.jpos.ee.pm.core.PresentationManager;

/**This class represents the leafs of the menu tree. It have a location and two flags indicating
 * if the item must be considered as "external" and if the item must be embed in jpos or not (this
 * flags could no apply for some PM engine.
 *
 * @author jpaoletti
 */
public class MenuItem extends Menu{
    
    /**Indicates the location of the destiny. 
     * @see MenuItemLocation */
    private MenuItemLocation location;

    /**Indicates the location value of the destiny. 
     * @see MenuItemLocation */
    private String location_value;

    /**
     * Default constructor with name
     * @param text The text of the menu item
     */
    public MenuItem(String text) {
        setText(text);
    }

    /**
     * @param location the location to set
     */
    public void setLocation(MenuItemLocation location) {
        this.location = location;
    }

    /**
     * @return the location
     */
    public MenuItemLocation getLocation() {
        return location;
    }

    /**
     * @param location_value the location_value to set
     */
    public void setLocation_value(String location_value) {
        this.location_value = location_value;
    }

    /**
     * @return the location_value
     */
    public String getLocation_value() {
        return location_value;
    }

    /**Recover from the service the location object and set it and the value to
     * this item.
     * @param location The id to look into the conficuration file pm.locations.xml
     * @param value The location value*/
    public void parseLocation(String location, String value) {
        setLocation_value(value);
        setLocation(PresentationManager.pm.getLocation(location));
    }    
}