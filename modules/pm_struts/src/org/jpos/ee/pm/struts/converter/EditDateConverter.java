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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;

/**Converter for date.<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditDateConverter">
 *     <operationId>edit (or add)</operationId>
 *     <properties>
 *         <property name="format" value="dd/MM/yyyy" />
 *     <properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class EditDateConverter extends EditStringConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        try {
            String value = ctx.getString(PM_FIELD_VALUE);
            if (value != null) {
                return getDateFormat().parse((String) value);
            }
        } catch (ParseException e) {
            ctx.getPresentationManager().error(e);
        }
        return null;
    }

    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        Field field = (Field) ctx.get(PM_FIELD);
        try {
            Date o = (Date) getValue(ctx.getSelected().getInstance(), field);
            return super.visualize("date_converter.jsp?format=" + normalize(javaToJavascriptDateFormat(getFormatString())) + "&value=" + getDateFormat().format(o));
        } catch (Exception e) {
            return super.visualize("date_converter.jsp?format=" + normalize(javaToJavascriptDateFormat(getFormatString())) + "&value=");
        }
    }

    /**
     * Return the format object of the date
     * @return The format
     */
    public DateFormat getDateFormat() {
        DateFormat df = new SimpleDateFormat(getFormatString());
        return df;
    }

    private String getFormatString() {
        return getConfig("format", "MM/dd/yyyy");
    }

    private String javaToJavascriptDateFormat(String s) {
        /*
         * The format can be combinations of the following:
         * d - day of month (no leading zero)
         * dd - day of month (two digit)
         * D - day name short
         * DD - day name long
         * m - month of year (no leading zero)
         * mm - month of year (two digit)
         * M - month name short
         * MM - month name long
         * y - year (two digit)
         * yy - year (four digit)
         */
        return s.replaceAll("yy", "y").replace('M', 'm');
    }
}
