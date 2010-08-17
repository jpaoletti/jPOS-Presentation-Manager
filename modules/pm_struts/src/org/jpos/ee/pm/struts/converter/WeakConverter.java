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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMStrutsContext;

/**
 * Converter for weak entities.
 *
 * Properties:
 *
 * <b>weak-entity</b> Id of the weak entity
 * <b>show-list</b> If true (default) show the list of items
 * <b>show-modify</b> If true (default) show a button to edit screen
 *
 * @author jpaoletti
 */
public class WeakConverter extends StrutsEditConverter {

    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        Field field = (Field) ctx.get(PM_FIELD);
        StringBuilder sb = new StringBuilder();
        sb.append("weak_converter.jsp?weakid=");
        sb.append(getConfig("weak-entity"));
        sb.append("&showlist=");
        sb.append(getConfig("show-list", "true"));
        sb.append("&showbutton=");
        sb.append(getConfig("show-modify", "true"));
        sb.append("&property=");
        sb.append(field.getProperty());

        return super.visualize(sb.toString());
    }

    public static Collection getCollection(PMStrutsContext ctx) throws PMException {
        final Collection collection = (Collection) ctx.getPresentationManager().get(ctx.getSelected().getInstance(), ctx.getRequest().getParameter("property"));
        final List<Object> result = new ArrayList<Object>();
        final Entity entity = getEntity(ctx);
        if (collection != null) {
            for (Object object : collection) {
                result.add(entity.getDataAccess().refresh(ctx, object));
            }
        }
        return result;
    }

    public static Entity getEntity(PMStrutsContext ctx) {
        return ctx.getPresentationManager().getEntity((String) ctx.getRequest().getParameter("weakid"));
    }
}
