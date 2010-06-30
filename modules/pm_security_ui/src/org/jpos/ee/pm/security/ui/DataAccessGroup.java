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
package org.jpos.ee.pm.security.ui;

import java.util.List;

import org.jpos.ee.pm.core.DataAccess;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUserGroup;

public class DataAccessGroup implements DataAccess {

    public void delete(PMContext ctx, Object object)  throws PMException {
        try {
            getConnector(ctx).removeGroup((PMSecurityUserGroup) object);
        } catch (PMSecurityException ex) {
            ctx.getPresentationManager().error(ex);
        }
    }
    
    public Object refresh(PMContext ctx, Object o) throws PMException {
        if(o!=null){
            PMSecurityUserGroup instance  = (PMSecurityUserGroup)o;
            return getItem(ctx, "", instance.getName());
        }else{
            return null;
        }
    }

    public Object getItem(PMContext ctx, String property, String value) throws PMException {
        try {
            return getConnector(ctx).getGroup(value);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
        }
        return null;
    }

    public List<?> list(PMContext ctx, EntityFilter filter, Integer from, Integer count) throws PMException {
        try {
            List<PMSecurityUserGroup> list = getConnector(ctx).getGroups();
            Integer f = (from == null)?0:from;
            Integer t = (count == null)?list.size():(from+count > list.size()?list.size():from+count);
            return list.subList(f, t);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
            return null;
        }
    }

    public void update(PMContext ctx, Object instance) throws PMException  {
        try {
            getConnector(ctx).updateGroup((PMSecurityUserGroup)instance);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
        }
    }

    public void add(PMContext ctx, Object instance) throws PMException  {
        try {
            getConnector(ctx).addGroup((PMSecurityUserGroup) instance);
        } catch (Exception e) {
            ctx.getPresentationManager().error(e);
        }
    }

    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

    public Long count(PMContext ctx) throws PMException {
        return new Long(list(ctx,null, null, null).size());
    }

    public EntityFilter createFilter(PMContext ctx) throws PMException {
        return new EntityFilter();
    }
}