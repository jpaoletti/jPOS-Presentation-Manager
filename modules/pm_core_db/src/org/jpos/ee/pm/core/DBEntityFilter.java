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
package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class DBEntityFilter extends EntityFilter {

    private List<Criterion> filters;

    public DBEntityFilter() {
        super();
        this.setFilters(new ArrayList<Criterion>());
    }

    @Override
    public void clear() {
        getFilters().clear();
    }

    @Override
    public void process(Entity entity) {
        debug("Processing filter");
        for (Field field : entity.getAllFields()) {
            //This is not the best way but works for now
            if (field.shouldDisplay("filter")) {
                List<Object> values = new ArrayList<Object>();
                for (Object o : getInstance().getInstances()) {
                    values.add(PresentationManager.pm.get(o, field.getProperty()));
                }
                debug("VALUES [" + field.getProperty() + "]: " + values);
                Criterion c = null;

                if (values.get(0) != null) {
                    c = getCompareCriterion(field.getId(), values.get(0));
                    if (c != null) {
                        getFilters().add(c);
                    }
                }
            }
        }
        debug("FILTERS: " + getFilters());
    }

    protected Criterion getCompareCriterion(String fid, Object converted) {
        switch (getFilterOperation(fid)) {
            case LIKE:
                if (converted instanceof String) {
                    return Restrictions.ilike(fid, "%" + converted + "%");
                } else {
                    return Restrictions.eq(fid, converted);
                }
            case GE:
                return Restrictions.ge(fid, converted);
            case GT:
                return Restrictions.gt(fid, converted);
            case LE:
                return Restrictions.le(fid, converted);
            case LT:
                return Restrictions.lt(fid, converted);
            case NE:
                return Restrictions.not(Restrictions.eq(fid, converted));
            default:
                return Restrictions.eq(fid, converted);
        }
    }

    public final void setFilters(List<Criterion> filters) {
        this.filters = filters;
    }

    public List<Criterion> getFilters() {
        return filters;
    }
}
