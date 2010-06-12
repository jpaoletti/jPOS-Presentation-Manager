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
package org.jpos.ee.pm.security.ui.converter;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.converter.IgnoreConvertionException;
import org.jpos.ee.pm.converter.ShowStringConverter;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

public class ShowRuleApplyToConverter extends ShowStringConverter{

    public Object build(Entity entity, Field field, Operation operation,
            EntityInstanceWrapper einstance, Object value)
            throws ConverterException {
        throw new IgnoreConvertionException("");
    }

    public String visualize(Entity entity, Field field, Operation operation,
            EntityInstanceWrapper einstance, String extra)
            throws ConverterException {
        Integer p = (Integer) getValue(einstance, field);
        switch (p) {
        case 0:
            return super.visualize("localized_string_converter.jsp?value=pm.secrule.applyTo.username",extra);
        case 1:
            return super.visualize("localized_string_converter.jsp?value=pm.secrule.applyTo.password",extra);
        default:
            return p.toString();
        }
    }
}