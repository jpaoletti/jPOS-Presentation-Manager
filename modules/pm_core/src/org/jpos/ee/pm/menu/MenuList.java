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

import java.util.ArrayList;
import java.util.List;

/**This class represents an internal node in the menu tree. It contains another menus (list or leaf)
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 **/
public class MenuList extends Menu{
    private List<Menu> submenus;

    /**
     * Default constructor with a new empty list of submenus
     */
    public MenuList() {
        submenus = new ArrayList<Menu>();
    }

    /**Add the given menu to the submenu list
     * @param m The submenu to add
     * @return The same added menu.*/
    public Menu add(Menu m){
        submenus.add(m);
        m.setParent(this);
        return m;
    }

    /**
     * @return the submenus
     */
    public List<Menu> getSubmenus() {
        return submenus;
    }

    /**
     * @param submenus the submenus to set
     */
    public void setSubmenus(List<Menu> submenus) {
        this.submenus = submenus;
    }
}