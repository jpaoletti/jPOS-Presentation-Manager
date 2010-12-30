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
package org.jpos.ee.pm.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class DataAccessEntityContainer implements DataAccess{

    public Object getItem(PMContext ctx, String property, String value) throws PMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<?> list(PMContext ctx, EntityFilter filter, Integer from, Integer count) throws PMException {
        PMSession pc = getParent(ctx);
        List<?> weaklist = new ArrayList(pc.getContainers());
        int f = (from == null)?0:from;
        int c = (int) ((count == null) ? count(ctx) : count);
        if(count(ctx) < c)  return weaklist;
        if(count(ctx) < f)  return null;
        int fpc = f + c;
        return weaklist.subList(f, (int) ((fpc > count(ctx)) ? count(ctx) : fpc));
    }

    public PMSession getParent(PMContext ctx) throws PMException {
        return (PMSession) ctx.getEntityContainer().getOwner().getSelected().getInstance();
    }

    public Long count(PMContext ctx) throws PMException {
        return new Long(getParent(ctx).getContainers().size());
    }

    public void delete(PMContext ctx, Object object) throws PMException {
        final EntityContainer ec = (EntityContainer) object;
        getParent(ctx).setContainer(ec.getId(), null);
    }

    public void update(PMContext ctx, Object instance) throws PMException {
        
    }

    public void add(PMContext ctx, Object instance) throws PMException {
        
    }

    public Object refresh(PMContext ctx, Object o) throws PMException {
        return o;
    }

    public EntityFilter createFilter(PMContext ctx) throws PMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
