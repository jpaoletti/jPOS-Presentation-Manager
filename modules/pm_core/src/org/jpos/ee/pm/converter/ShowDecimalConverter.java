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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for BigDecimals and amounts <br>
 * Properties: currency and format
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowDecimalConverter">
 *     <operationId>show</operationId>
 *     <properties>
 *         <property name="currency" value="U$S" />
 *         <property name="format" value="0.00" />
 *         <property name="null-value" value="$ 0.00" />
 *     </properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowDecimalConverter extends ShowStringConverter {
    
    public Object build(Entity entity, Field field, Operation operation,
            EntityInstanceWrapper einstance, Object value) throws ConverterException {
        throw new IgnoreConvertionException("");
    }

    public String visualize(Entity entity, Field field, Operation operation,EntityInstanceWrapper einstance, String extra) throws ConverterException {
        BigDecimal o = null;
        try{
            o = (BigDecimal) getValue(einstance.getInstance(),field);
        }catch (Exception e) {}
        NumberFormat formatter = new DecimalFormat(getConfig("format", "#0.00"));
        if(o==null)
            return getConfig("null-value","0.00");
        else
            return visualize(formatter.format(o),extra);
    }
}

