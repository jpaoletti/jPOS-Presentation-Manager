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
import org.jpos.ee.pm.converter.ShowStringConverter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class ShowPreConverter extends ShowStringConverter{

    public String visualize(PMContext ctx) throws ConverterException {
        EntityInstanceWrapper einstance = (EntityInstanceWrapper) ctx.get(PM_ENTITY_INSTANCE_WRAPPER);
        Field field = (Field) ctx.get(PM_FIELD);
        Object o = getValue(einstance.getInstance(), field);
        return super.visualize("pre.jsp?value="+o,ctx.getString(PM_EXTRA_DATA));
    }
}
