/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
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

package org.jpos.ee.pm.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for date.<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowBooleanConverter">
 *     <operationId>show</operationId>
 *     <properties>
 *         <property name="format" value="dd/MM/yyyy" />
 *     <properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowDateConverter extends Converter {
    
    public String toString (Object obj)  throws ConverterException{
        return obj != null ? getDateFormat().format (obj) : null;
    }

    public Object fromString (String s)  throws ConverterException{
        try {
            if (s != null)
                return getDateFormat().parse (s);
        } catch (ParseException e) {
        	throw new ConverterException("pm_core.converter.not.date");
        }
        return null;
    }
    
    public String toEdit (Entity e, Object obj, Field f, String append)  throws ConverterException{
        Object p = getNestedProperty (obj, f.getId());
        StringBuilder sb = new StringBuilder ("<input");
        if (append.length() > 0) {
            sb.append (' ');
            sb.append (append);
        }
        String id = "f_" + f.getId();
        sb.append (" type='text' id='");
        sb.append (id);
        sb.append ("' name='");
        sb.append (id);
        if (p != null) {
            sb.append ("' value='");
            sb.append (toString(p));
        }
        sb.append ("' size='10' maxlength='10'");
        if (!f.canUpdate())
            sb.append (" disabled='true'");
        sb.append ("/>");

        if (f.canUpdate()) {
            sb.append ("<script type='text/javascript'>");
            sb.append ("$(document).ready(function() {");
            sb.append ("$('#");
            sb.append (id);
            sb.append ("').datepicker({buttonImage: '/jposee/images/calendar.gif',buttonImageOnly: true, buttonText: '', showOn: 'both', speed: 'fast'");
            String defaultDate = getConfig ("defaultDate");
            if (defaultDate != null) {
                sb.append (", defaultDate: ");
                sb.append (defaultDate);
            }
            sb.append ("});");
            sb.append ("});");
            sb.append ("</script>");
        }
        return sb.toString();
    }

    public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
    	throw new IgnoreConvertionException("");
	}
	
    public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
    	Date o = (Date) getValue(einstance.getInstance(), field);
		return getDateFormat().format(o);
	}
    public DateFormat getDateFormat() {
        DateFormat df = new SimpleDateFormat (getConfig("format", "MM/dd/yyyy"));
        return df;
    }
}