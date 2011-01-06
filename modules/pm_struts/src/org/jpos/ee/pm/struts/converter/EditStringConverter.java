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
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class EditStringConverter extends StrutsEditConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        final PMStrutsContext c = (PMStrutsContext) ctx;
        final Object value = ctx.get(PM_FIELD_VALUE);
        final String fid = ((Field) ctx.get(PM_FIELD)).getId();
        final boolean isNull = Boolean.valueOf((String)c.getParameter("f_" + fid + "_null"));
        if (isNull) {
            return null ;
        } else {
            return (value != null) ? value.toString() : null;
        }
    }

    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        final EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        final Field field = (Field) ctx.get(PM_FIELD);
        final Object p = getValue(einstance, field);
        final String value = normalize((p == null) ? "" : p.toString());
        return super.visualize("string_converter.jsp?"
                + "ml=" + getConfig("max-length")
                + "&value=" + value
                + "&isNull=" + (p == null)
                + "&withNull=" + getConfig("with-null", "false"));
    }

    public String normalize(String s) {
        StringBuilder str = new StringBuilder();

        int len = (s != null) ? s.length() : 0;
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '<':
                    str.append("&lt;");
                    break;
                case '>':
                    str.append("&gt;");
                    break;
                case '&':
                    str.append("&amp;");
                    break;
                /*
                case '"': 
                str.append("&quot;");
                break;
                 */
                case '\'':
                    str.append("&apos;");
                    break;
                case '\r':
                case '\n':
                    str.append("&#x");
                    str.append(Integer.toHexString((int) (ch & 0xFF)));
                    str.append(';');
                    break;
                default:
                    if (ch < 0x20) {
                        str.append("&#x");
                        str.append(Integer.toHexString((int) (ch & 0xFF)));
                        str.append(';');
                    } else if (ch > 0xff00) {
                        str.append((char) (ch & 0xFF));
                    } else {
                        str.append(ch);
                    }
            }
        }
        return (str.toString());
    }
}
