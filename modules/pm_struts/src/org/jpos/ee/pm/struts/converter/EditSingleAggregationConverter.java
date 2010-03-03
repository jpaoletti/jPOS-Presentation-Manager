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
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;

public class EditSingleAggregationConverter extends StrutsEditConverter {

	public Object build(PMContext ctx) throws ConverterException {
		try{
			String s = ctx.getString(PM_FIELD_VALUE);
			if(s.trim().compareTo("")==0) return null;
			Integer x = Integer.parseInt(s);
			if(x==-1) return null;
			String eid = getConfig("entity");
			PMEntitySupport es = PMEntitySupport.getInstance();
			Entity e = es.getPmservice().getEntity(eid);
			if(e==null) throw new ConverterException("Cannot find entity "+eid);
			List<?> list = e.getList(ctx);
			return list.get(x);
		} catch (Exception e1) {
			PMLogger.error(e1);
			throw new ConverterException("Cannot convert single aggregation");
		}
	}

	public String visualize(PMContext ctx) throws ConverterException {
		String wn = getConfig("with-null", "false");
		boolean withNull= (wn==null || wn.compareTo("true")!=0)?false:true;
		return super.visualize("single_aggregation_converter.jsp?filter="+getConfig("filter")+"&entity="+getConfig("entity")+"&with_null="+withNull);
	}

}
