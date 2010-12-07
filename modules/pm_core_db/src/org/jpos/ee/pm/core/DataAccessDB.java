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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jpos.ee.Constants;
import org.jpos.ee.DB;

public class DataAccessDB implements DataAccess, Constants {

    public Object getItem(PMContext ctx, String property, String value) throws PMException {
        try {
            DB db = getDb(ctx);
            Criteria c = db.session().createCriteria(Class.forName(getEntity(ctx).getClazz()));
            c.setMaxResults(1);
            c.add(Restrictions.sqlRestriction(property + "=" + value));
            return c.uniqueResult();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    protected DB getDb(PMContext ctx) {
        return (DB) ctx.get(DBPersistenceManager.PM_DB);
    }

    private Entity getEntity(PMContext ctx) throws PMException {
        if (ctx.get(PM_ENTITY) == null) {
            return ctx.getEntity();
        } else {
            return (Entity) ctx.get(PM_ENTITY);
        }
    }

    public List<?> list(PMContext ctx, EntityFilter filter, Integer from, Integer count) throws PMException {
        //We use the filter only if the entity we use is the container one.
        Criteria list = createCriteria(ctx, getEntity(ctx), filter);
        if (count != null) {
            list.setMaxResults(count);
        }
        if (from != null) {
            list.setFirstResult(from);
        }
        return list.list();
    }

    public void delete(PMContext ctx, Object object) throws PMException {
        DB db = getDb(ctx);
        db.session().delete(object);
    }

    public void update(PMContext ctx, Object object) throws PMException {
        DB db = getDb(ctx);
        db.session().update(object);
    }

    public void add(PMContext ctx, Object object) throws PMException {
        try {
            DB db = getDb(ctx);
            db.session().save(object);
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            throw new PMException("constraint.violation.exception");
        }
    }

    public Long count(PMContext ctx) throws PMException {
        EntityFilter filter = ctx.getEntityContainer().getFilter();
        Criteria count = createCriteria(ctx, getEntity(ctx), filter);
        count.setProjection(Projections.rowCount());
        count.setMaxResults(1);
        return (Long) count.uniqueResult();
    }

    protected Criteria createCriteria(PMContext ctx, Entity entity, EntityFilter filter) throws PMException {
        Criteria c;
        DB db = getDb(ctx);
        try {
            c = db.session().createCriteria(Class.forName(entity.getClazz()));
        } catch (ClassNotFoundException e) {
            ctx.getErrors().add(new PMMessage(ENTITY,"class.not.found"));
            throw new PMException();
        }

        String order = null;
        try {
            order = (ctx.getString(PM_LIST_ORDER) != null) ? entity.getFieldById(ctx.getString(PM_LIST_ORDER)).getProperty() : null;
        } catch (Exception e) {
        }
        boolean asc = (ctx.get(PM_LIST_ASC) == null) ? true : (Boolean) ctx.get(PM_LIST_ASC);
        //This is a temporary patch until i found how to sort propertys
        if (order != null && order.contains(".")) {
            order = order.substring(0, order.indexOf("."));
        }
        if (order != null && order.compareTo("") != 0) {
            if (asc) {
                c.addOrder(Order.asc(order));
            } else {
                c.addOrder(Order.desc(order));
            }
        }

        if (entity.getListfilter() != null) {
            c.add((Criterion) entity.getListfilter().getListFilter(ctx));
        }

        if (filter != null) {
            c = ((DBEntityFilter) filter).applyFilters(c);
        }
        //Weak entities must filter the parent
        if (entity.isWeak()) {
            if (ctx.getEntityContainer().getOwner() != null && ctx.getEntityContainer().getOwner().getSelected() != null) {
                final Object instance = ctx.getEntityContainer().getOwner().getSelected().getInstance();
                final String localProperty = entity.getOwner().getLocalProperty();
                c.add(Restrictions.eq(localProperty, instance));
            }
        }

        return c;
    }

    public Object refresh(PMContext ctx, Object o) throws PMException {
        DB db = getDb(ctx);
        final Object merged = db.session().merge(o);
        db.session().refresh(merged);
        return merged;
    }

    public EntityFilter createFilter(PMContext ctx) throws PMException {
        return new DBEntityFilter();
    }
}
