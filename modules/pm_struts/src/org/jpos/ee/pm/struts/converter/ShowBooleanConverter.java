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

import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;

/**Converter for showing a boolean value.<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowBooleanConverter" operations="show list">
 *     <properties>
 *         <property name="true-text" value="pm.true.text" />
 *         <property name="false-text" value="pm.false.text" />
 *     <properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowBooleanConverter extends Converter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        return Boolean.valueOf(ctx.getString(PM_FIELD_VALUE));
    }
    
    @Override
    public String visualize(PMContext ctx) throws ConverterException {
    	EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        Object value = getValue(einstance.getInstance(),(Field) ctx.get(PM_FIELD) );
        if(! (value instanceof Boolean)) throw new ConverterException("invalid.conversion");
		boolean b = ((Boolean) value).booleanValue();
        if(b)
        	return super.visualize("localized_string_converter.jsp?value="+getConfig("true-text", "pm.converter.boolean_converter.yes"),"");
        else
        	return super.visualize("localized_string_converter.jsp?value="+getConfig("false-text", "pm.converter.boolean_converter.no"),"");
    }
}