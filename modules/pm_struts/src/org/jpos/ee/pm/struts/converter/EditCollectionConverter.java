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

import java.util.Collection;
import java.util.List;
import org.jpos.core.ConfigurationException;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class EditCollectionConverter extends AbstractCollectionConverter {

    public Object build(PMContext ctx) throws ConverterException {
        try{
            final PMStrutsContext c = (PMStrutsContext) ctx;
            Collection<Object> result = getCollection(ctx);
            List<?> list = recoverList(c, getConfig("entity"), true);

            String s = ctx.getString(PM_FIELD_VALUE);
            if(s.trim().compareTo("")==0) return result;
            String[] ss = s.split(";");
            if(ss.length > 0 ){
                for (int i = 0; i < ss.length; i++) {
                    Integer x = Integer.parseInt(ss[i]);
                    result.add(list.get(x));
                }
            }
            return result;
        } catch (ConverterException e2) {
            throw e2;
        } catch (Exception e1) {
            PMLogger.error(e1);
            throw new ConverterException("pm.struts.converter.cant.convert.collection");
        }
    }

    protected Collection<Object> getCollection(PMContext ctx) throws ConverterException, ConfigurationException {
        String collection_class = getConfig("collection-class");
        if (collection_class == null) {
            throw new ConverterException("pm.struts.converter.class.mustbedefined");
        }
        Collection<Object> result = null;
        Field field = (Field) ctx.get(PM_FIELD);
        result = (Collection<Object>) EntitySupport.get(ctx.get(PM_ENTITY_INSTANCE), field.getId());
        if (result == null) {
            result = (Collection<Object>) PMEntitySupport.getInstance().getPmservice().getFactory().newInstance (collection_class);
        }
        return result;
    }

    public String visualize(PMContext ctx) throws ConverterException {
        final String filter = getConfig("filter");
        final String entity = getConfig("entity");
        saveList((PMStrutsContext) ctx,entity);
        return super.visualize("collection_converter.jsp?filter="+filter+"&entity="+entity);
    }
}
