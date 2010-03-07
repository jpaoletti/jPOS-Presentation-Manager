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

import java.util.List;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.ListFilter;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;

/**Converter for integer <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditSingleAggregationConverter">
 * 	<operationId>edit</operationId>
 *     	<properties>
 *     		<property name="entity" 	value="the_other_entity" />
 *     		<property name="with-null" 	value="true" />
 *     		<property name="filter" 	value="field1=v1 , field2=v2" />
 * 		</properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */

public class EditSingleAggregationConverter extends StrutsEditConverter {

	public Object build(PMContext ctx) throws ConverterException {
		try{
			String s = ctx.getString(PM_FIELD_VALUE);
			if(s.trim().compareTo("")==0) return null;
			Integer x = Integer.parseInt(s);
			if(x==-1) return null;
			
			String eid = getConfig("entity");
			String f = getConfig("filter");
			
			List<?> list = getList(eid, f , ctx);
			return list.get(x);
		} catch (Exception e1) {
			PMLogger.error(e1);
			throw new ConverterException("Cannot convert single aggregation");
		}
	}

	public static List<?> getList(String eid, String f, PMContext ctx) throws ConverterException,
			PMException {
		
		ListFilter filter = null;
		
		if( f != null && f.compareTo("null") != 0) {
			filter = (ListFilter) EntitySupport.newObjectOf(f);
		}
				
		PMEntitySupport es = PMEntitySupport.getInstance();
		Entity e = es.getPmservice().getEntity(eid);
		List<?> list = null;
		if(e==null) throw new ConverterException("Cannot find entity "+eid);
		synchronized (e) {
			ListFilter tmp = e.getListfilter();
			e.setListfilter(filter);
			ctx.debug("Filter: "+filter);
			list = e.getList(ctx,null,null,null);
			e.setListfilter(tmp);
		}
		return list;
	}

	public String visualize(PMContext ctx) throws ConverterException {
		String wn = getConfig("with-null", "false");
		boolean withNull= (wn==null || wn.compareTo("true")!=0)?false:true;
		return super.visualize("single_aggregation_converter.jsp?filter="+getConfig("filter")+"&entity="+getConfig("entity")+"&with_null="+withNull);
	}

}
