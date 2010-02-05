package org.jpos.ee.pm.core.report;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jpos.ee.pm.core.Field;

public class ReportFilterTypeRange implements ReportFilterType {

	public Criterion getFilter(Field field, List<Object> values) {
		return Restrictions.between(field.getId(), values.get(0), values.get(1));
	}
	
	public Integer getParameterCount() {
		return 2;
	}

}
