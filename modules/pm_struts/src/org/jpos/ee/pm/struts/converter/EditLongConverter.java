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

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;

/**Converter for long <br>
 * Properties: currency and format
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditLongConverter">
 *     <operationId>edit</operationId>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class EditLongConverter extends EditStringConverter {
    
    public Object build(PMContext ctx) throws ConverterException{
        try {
            return Long.parseLong(ctx.getString(PM_FIELD_VALUE));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}