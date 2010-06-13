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
package org.jpos.ee.pm.converter;

import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.iso.ISOUtil;

/**Converter for Integer that must be padded<br>
 * Properties: currency and format
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowPaddedIntegerConverter">
 *     <operationId>show</operationId>
 *     <properties>
 *         <property name="count" value="3" />
 *     </properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowPaddedIntegerConverter extends Converter {
    
    public Object build(PMContext ctx) throws ConverterException {
        throw new IgnoreConvertionException("");
    }

    public String visualize(PMContext ctx) throws ConverterException {
        EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        Field field = (Field) ctx.get(PM_FIELD);
        Integer i = (Integer) getValue(einstance.getInstance(), field);
        
        try {
            return ISOUtil.zeropad(i.toString(), Integer.parseInt(getConfig("count","3")));
        } catch (Exception e) {
            return i.toString();
        }
    }
}