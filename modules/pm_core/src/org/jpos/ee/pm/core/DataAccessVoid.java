package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.List;

/**An implementation of Data Access that simply do nothing
 * 
 * @author jpaoletti
 * */
public class DataAccessVoid implements DataAccess {

	
	public void add(PMContext ctx, Object instance) throws PMException {

	}

	
	public Long count(PMContext ctx) throws PMException {
		return 0L;
	}

	
	public EntityFilter createFilter(PMContext ctx) throws PMException {
		return new EntityFilter();
	}

	
	public void delete(PMContext ctx, Object object) throws PMException {

	}

	
	public Object getItem(PMContext ctx, String property, String value)throws PMException {
		return null;
	}

	public List<?> list(PMContext ctx, EntityFilter filter, Integer from,Integer count) throws PMException {
		return new ArrayList<Object>();
	}

	public Object refresh(PMContext ctx, Object o) throws PMException {
		return o;
	}

	
	public void update(PMContext ctx, Object instance) throws PMException {

	}
}
