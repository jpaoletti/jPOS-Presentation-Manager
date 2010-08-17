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
package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.menu.MenuItem;
import org.jpos.ee.pm.menu.MenuItemLocation;

/**
 * Location representing external links.
 * 
 * @author jpaoletti
 */
public class MenuItemLocationExternal implements MenuItemLocation {

    public Object build(MenuItem item, Object... params) {
        MenuItemContext context = new MenuItemContext();
        StringBuilder sb = new StringBuilder("<a target='_blank' href=");
        sb.append("'");
        sb.append(buildLink(item, params));
        sb.append("'");
        sb.append(">");
        context.setPrefix(sb.toString());
        context.setValue(item.getText());
        context.setSufix("</a>");
        return context;
    }

    /**
     * Builds an external link
     * @param item The menu item
     * @param params Extra parameters
     * @return The string with the external link
     */
    protected String buildLink(MenuItem item, Object... params) {
        return item.getLocation_value();
    }
}
