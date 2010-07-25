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

/**This simple interface represents a MenuItem location, that is "where should this item points in 
 * the menu?" This is an abstraction of a location, and must be implemented on each PM implementation.
 *   
 * <pre>
 * {@code
 *         <menu-item text="xxxx" perm="xxxx">
 *             <location id="some_location_id" value="some_value" />
 *             ...
 *         </menu-item>
 * }
 * </pre>
 * 
 * Each implementation of this interface must be defined in a location file with the id and class
 * that implements this interface. The idea is that each location builds its own "pointer" on each
 * menu leaf.
 * 
 * The configuration file for locations has this general form:
 * 
 * <pre>
 * {@code
 * <?xml version='1.0' ?>
 * <!DOCTYPE entity SYSTEM "location.dtd">
 * <locations>
 *     <location id="yyyyy" class="org.jpos.ee.pm.yyyy.MenuItemLocationXxxxx"/>
 *     <location id="xxxxx" class="org.jpos.ee.pm.xxxx.MenuItemLocationYyyyy"/>
 *     ...
 * </locations>
 * }
 * </pre> 
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public interface MenuItemLocation {

    /**
     * Builds a custom representation of the goal of a menu item
     * @param item The menu item
     * @param params Generic parameters.
     * @return The representation of the link
     */
    public Object build(MenuItem item, Object... params);
}
