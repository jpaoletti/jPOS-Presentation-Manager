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
 * Location for internal links that uses loadPage javascript function
 *
 * @author jpaoletti
 */
public class MenuItemLocationStruts implements MenuItemLocation {

    public Object build(MenuItem item, Object... params) {
        MenuItemContext context = new MenuItemContext();
        StringBuilder sb = new StringBuilder("<a href=");
        String link = buildLink(item, params);
        sb.append("javascript:loadPage('");
        sb.append(link);
        sb.append("')");
        sb.append(">");
        context.setPrefix(sb.toString());
        context.setValue(item.getText());
        context.setSufix("</a>");
        return context;
    }

    private String buildLink(MenuItem item, Object... params) {
        return (String) params[0] + item.getLocation_value();
    }
}
