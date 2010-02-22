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
package org.jpos.ee.pm.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.jpos.ee.Constants;
import org.jpos.ee.DB;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;

/**@deprecated*/
public class PMListSupport implements Constants {
	private Integer total;
	private Integer rpp;


	public List<Object> getContentList(PMContext ctx, Entity entity, EntityFilter filter, PMList pmlist) throws PMException{
		Criteria list  = createCriteria(ctx, entity,filter,pmlist);
		Criteria count = createCriteria(ctx, entity,filter,pmlist);
		rpp = (pmlist!=null)?pmlist.rpp():DEFAULT_PAGE_SIZE;
		Integer fr= (pmlist!=null)?pmlist.from():0;
		
		count.setProjection(Projections.rowCount());
		list.setMaxResults(getRpp());
		list.setFirstResult(fr);
		
		count.setMaxResults(1);
		
		total = (Integer) count.uniqueResult();
		if(total==null)total = 0;
		
		return list.list();
	}
	
	protected Criteria createCriteria(PMContext ctx, Entity entity, EntityFilter filter, PMList pmlist) throws PMException{
		Criteria c;
		DB db = (DB) ctx.get(DB);
		try {
			c = db.session().createCriteria(Class.forName(entity.getClazz()));
		} catch (ClassNotFoundException e) {
			//TODO finish
			ctx.getErrors().add(new PMMessage(""));
			throw new PMException();
		}
		
		if(pmlist!=null){
			String order = pmlist.getOrder();
			//This is a temporary patch until i found how to sort propertys
			if(order!=null && order.contains(".")) order = order.substring(0, order.indexOf("."));
			if(order!= null && order.compareTo("")!=0){
				if(pmlist.isDesc()) 	c.addOrder(Order.desc(order));
				else			c.addOrder(Order.asc (order));
			}
		}
		if(entity.getListfilter() != null) {
			c.add(entity.getListfilter().getListCriteria());
		}
		
		if(filter != null){
			for(Criterion criterion : filter.getFilters()){
				c.add(criterion);
			}
		}
		return c;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public Map<String, Object> buildFilterMap(List<Field> fields, Object filter) {
		Map<String,Object> r = new HashMap<String, Object>();
		for(Field field : fields){
			Object object = EntitySupport.get(filter, field.getId());
			if(object!=null){
				if(!(object instanceof String) || ((String)object).compareTo("")!=0){
					r.put(field.getId(), object);
				}
			}
		}
		return r;
	}

	public void setRpp(Integer rpp) {
		this.rpp = rpp;
	}

	public Integer getRpp() {
		return rpp;
	}
}