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

import java.util.Collection;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.PMLogger;

/**Converter for a collection (1..* aggregation).<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowCollectionConverter">
 *     <operationId>show</operationId>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowCollectionConverter extends Converter {

	public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		return value;
	}

	public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
		try {
			Collection<?> list = (Collection<?>)EntitySupport.get(einstance.getInstance(), field.getId());
			StringBuilder sb = new StringBuilder();
			sb.append("<ul>");
			for(Object o:list){
				sb.append("<li>");
				sb.append(o.toString());
				sb.append("</li>");
			}
			sb.append("</ul>");
			return sb.toString();
		} catch (Exception e1) {
			PMLogger.error(e1);
			throw new ConverterException("pm_core.converter.not.collection");
		}
	}
}