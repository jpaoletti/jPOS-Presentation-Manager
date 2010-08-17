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

import java.util.Collection;
import java.util.List;
import org.jpos.core.ConfigurationException;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.struts.PMStrutsContext;

/**
 * Converter for a collection of objects of another strong entity
 * 
 * @author jpaoletti
 */
public class EditCollectionConverter extends AbstractCollectionConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        try{
            final PMStrutsContext c = (PMStrutsContext) ctx;
            Collection<Object> result = getCollection(ctx);
            List<?> list = recoverList(c, getConfig("entity"), true);

            result.clear();
            String s = ctx.getString(PM_FIELD_VALUE);
            if(s.trim().compareTo("")==0) return result;
            String[] ss = s.split(";");
            if(ss.length > 0 ){
                for (int i = 0; i < ss.length; i++) {
                    Integer x = Integer.parseInt(ss[i]);
                    if(!result.contains(list.get(x)))
                        result.add(list.get(x));
                }
            }
            return result;
        } catch (ConverterException e2) {
            throw e2;
        } catch (Exception e1) {
            ctx.getPresentationManager().error(e1);
            throw new ConverterException("pm.struts.converter.cant.convert.collection");
        }
    }

    /**
     * Return the collection from the entity instance (or a new one if its null)
     * 
     * @param ctx The context
     * @return The collection to edit
     * @throws ConverterException
     * @throws ConfigurationException
     */
    protected Collection<Object> getCollection(PMContext ctx) throws ConverterException, ConfigurationException {
        String collection_class = getConfig("collection-class");
        if (collection_class == null) {
            throw new ConverterException("pm.struts.converter.class.mustbedefined");
        }
        final Object instance = ctx.get(PM_ENTITY_INSTANCE);
        Collection<Object> result = null;
        Field field = (Field) ctx.get(PM_FIELD);
        result = (Collection<Object>) getValue(instance, field);
        if (result == null) {
            result = (Collection<Object>) ctx.getPresentationManager().newInstance(collection_class);
        }
        return result;
    }

    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        final String filter = getConfig("filter");
        final String entity = getConfig("entity");
        final Field field = (Field) ctx.get(PM_FIELD);
        saveList((PMStrutsContext) ctx,entity);
        return super.visualize("collection_converter.jsp?filter="+filter+"&entity="+entity+"&prop="+field.getProperty());
    }
}
