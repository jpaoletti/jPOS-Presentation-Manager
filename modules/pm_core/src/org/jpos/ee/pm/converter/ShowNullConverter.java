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

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for eventual null objects. This can be optimized to any object and not only for strings,
 * but it requires another converter. <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowNullConverter">
 *     <operationId>show</operationId>
 *     <properties>
 *         <property name="null-value" value="-nothing-" />
 *     <properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowNullConverter extends ShowStringConverter{
    
	public Object build(Entity entity, Field field, Operation operation, EntityInstanceWrapper einstance, Object value) throws ConverterException{
		return (value!=null)?value.toString():null;
	}

	public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
		String o = super.visualize(entity, field, operation, einstance, extra);
		if(o == null)
			return getConfig("null-value", "-");
		else return o;
	}
}