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

import java.util.List;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.struts.PMStrutsContext;

/**Converter for integer <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditSingleAggregationConverter">
 *     <operationId>edit</operationId>
 *         <properties>
 *             <property name="entity"         value="the_other_entity" />
 *             <property name="with-null"      value="true" />
 *             <property name="filter"         value="org.jpos.ee.pm.core.ListFilterXX" />
 *             <property name="sort-field"     value="xxx" />
 *             <property name="sort-direction" value="asc | desc" />
 *         </properties>
 * </converter>
 * }
 * </pre>
 * @author jaoletti jeronimo.paoletti@gmail.com
 * */

public class EditSingleAggregationConverter extends AbstractCollectionConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        try{
            String s = ctx.getString(PM_FIELD_VALUE);
            if(s==null || s.trim().compareTo("")==0) return null;
            Integer x = Integer.parseInt(s);
            if(x==-1) return null;            
            List<?> list = recoverList((PMStrutsContext) ctx,getConfig("entity"),true);
            return list.get(x);
        } catch (Exception e1) {
            ctx.getPresentationManager().error(e1);
            throw new ConverterException("Cannot convert single aggregation");
        }
    }
    
    @Override
    public String visualize(PMContext ctx) throws ConverterException {
        String wn = getConfig("with-null", "false");
        boolean withNull= (wn==null || wn.compareTo("true")!=0)?false:true;
        final String filter = getConfig("filter");
        final String entity = getConfig("entity");
        Field field = (Field) ctx.get(PM_FIELD);
        saveList((PMStrutsContext) ctx,entity);
        return super.visualize("single_aggregation_converter.jsp?filter="+filter+"&entity="+entity+"&with_null="+withNull+"&prop="+field.getProperty());
    }

}
