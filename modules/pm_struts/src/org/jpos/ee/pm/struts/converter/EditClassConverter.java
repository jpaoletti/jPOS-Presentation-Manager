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
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.struts.PMEntitySupport;

public class EditClassConverter  extends StrutsEditConverter{

    public Object build(PMContext ctx) throws ConverterException {
        Object value = ctx.get(PM_FIELD_VALUE);
        if(value ==null) return null;
        String s = (String) value;
        if(s.compareTo("")==0)return null;
        try {
            return EntitySupport.newObjectOf(s);
        } catch (Exception e) {
            throw new ConverterException(e.getMessage());
        }
    }

    public String visualize(PMContext ctx) throws ConverterException {
        EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        Field field = (Field) ctx.get(PM_FIELD);
        String s = "";
        try {
            s = PMEntitySupport.get(einstance.getInstance(), field.getId()).getClass().getName();
        } catch (Exception e) {
        }
        return super.visualize("string_converter.jsp?value="+s);
    }
}