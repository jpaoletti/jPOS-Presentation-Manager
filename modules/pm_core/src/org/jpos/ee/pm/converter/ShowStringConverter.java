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

import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;

/**Converter for strings <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowStringConverter">
 *     <operationId>show</operationId>
 *     <properties>
 *          <property name="prefix" value="" />
 *          <property name="suffix" value="" />
 *     </properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowStringConverter extends Converter {

    public Object build(PMContext ctx) throws ConverterException{
        throw new IgnoreConvertionException("");
    }

    public String visualize(Object obj, String extra) throws ConverterException{
        String prefix = getConfig("prefix");
        String suffix = getConfig("suffix");
        String res = obj != null ? obj.toString() : "";
        if(!res.equals("")){
            if(prefix!=null) res = prefix + res;
            if(suffix!=null) res = res+suffix;
        }
        return res;
        
    }
    public String visualize(PMContext ctx) throws ConverterException {
        EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        Field field = (Field) ctx.get(PM_FIELD);
        Object o = getValue(einstance.getInstance(), field);
        return visualize(o, ctx.getString(PM_EXTRA_DATA));
    }
}