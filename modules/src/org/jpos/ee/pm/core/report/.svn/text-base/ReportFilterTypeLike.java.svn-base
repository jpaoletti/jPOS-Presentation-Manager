package org.jpos.ee.pm.core.report;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jpos.ee.pm.core.Field;

public class ReportFilterTypeLike implements ReportFilterType {

	public Criterion getFilter(Field field, List<Object> values) {
		return Restrictions.ilike(field.getId(), values.get(0));
	}

	public Integer getParameterCount() {
		return 1;
	}

}
