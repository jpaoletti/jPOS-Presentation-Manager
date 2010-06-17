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
package org.jpos.ee.pm.struts.converter;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.converter.IgnoreConvertionException;
import org.jpos.ee.pm.core.PMContext;

public class WeakConverter extends StrutsEditConverter {

    public Object build(PMContext ctx) throws ConverterException {
        throw new IgnoreConvertionException("");
    }

    public String visualize(PMContext ctx) throws ConverterException {
        StringBuilder sb = new StringBuilder();
        sb.append("weak_converter.jsp?weakid=");
        sb.append(getConfig("weak-entity"));
        sb.append("&showlist=");
        sb.append(getConfig("show-list", "true"));
        sb.append("&showbutton=");
        sb.append(getConfig("show-modify", "true"));
        return super.visualize(sb.toString());
    }
}
