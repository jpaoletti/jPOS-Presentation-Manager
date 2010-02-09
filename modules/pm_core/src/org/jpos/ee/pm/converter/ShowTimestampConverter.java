/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

public class ShowTimestampConverter extends Converter {
    transient DateFormat df;
    
    public String toString (Object obj)  throws ConverterException{
        return obj != null ? getDateFormat().format (obj) : null;
    }
    public Object fromString (String s)  throws ConverterException{
        try {
            if (s != null)
                return getDateFormat().parse (s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String toEdit (Entity e, Object obj, Field f, String append)  throws ConverterException {
        Object p = getNestedProperty (obj, f.getId());
        StringBuilder sb = new StringBuilder ("<input");
        if (append.length() > 0) {
            sb.append (' ');
            sb.append (append);
        }
        String id = e.getId() + "_" + f.getId();
        sb.append (" type='text' id='");
        sb.append (id);
        sb.append ("' name='");
        sb.append (id);
        if (p != null) {
            sb.append ("' value='");
            sb.append (toString(p));
        }
        sb.append ("' size='18' maxlength='18'");
        if (!f.canUpdate())
            sb.append (" disabled='true'");
        sb.append ("/>");
        return sb.toString();
    }
    public DateFormat getDateFormat() {
        if (df == null) {
            df = new SimpleDateFormat ("MM/dd/yyyy hh:mm:ss");
        }
        return df;
    }
	
    public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}
	
    public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}
}

