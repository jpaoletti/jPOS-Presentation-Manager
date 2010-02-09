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
package org.jpos.ee.pm.struts.converter;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for integer <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditIntegerConverter">
 *     <operationId>edit</operationId>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class EditIntegerConverter extends EditStringConverter {
	
	public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		try {
            return Integer.parseInt((String)value);
        } catch (NumberFormatException e) {
            return null;
        }
	}
}