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
package org.jpos.ee.pm.struts.actions;

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.EntityOwner;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class AddAction extends RowActionSupport {
    
    public boolean testSelectedExist() { return false; }
    protected boolean openTransaction() { return true; }

    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        if(ctx.getParameter(FINISH)==null){
            //Creates bean and put it in session
            Object obj;
            try {
                obj = getPMService().getFactory().newInstance (ctx.getEntity().getClazz());
                ctx.getEntityContainer().setSelected(new EntityInstanceWrapper(obj));
                ctx.getEntityContainer().setSelectedNew(true);
                throw new PMForwardException(CONTINUE);
            } catch (ConfigurationException e) {
                ctx.getPresentationManager().error(e);
                throw new PMException("pm_core.unespected.error");
            }
        }
        if(ctx.getSelected() == null){
            throw new PMException("pm.instance.not.found");
        }
        for (Field f : ctx.getEntity().getAllFields()) {
            if(f.shouldDisplay(ctx.getOperation().getId()))
                proccessField(ctx, f, ctx.getSelected());
        }
        if(!ctx.getErrors().isEmpty())
            throw new PMException();

        if(ctx.getEntity().isWeak()){
            final Object parent = ctx.getEntityContainer().getOwner().getSelected().getInstance();
            final EntityOwner owner = ctx.getEntity().getOwner();
            final Object instance = ctx.getSelected().getInstance();
            ctx.getPresentationManager().set(instance,owner.getLocalProperty(), parent);
            getOwnerCollection(ctx).add(instance);
        }
        
        return true;
    }

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        Object instance = ctx.getSelected().getInstance();
        ctx.getPresentationManager().debug(this,"Saving '"+ctx.getEntity().getId()+"' to Data Access");
        ctx.getEntity().getDataAccess().add(ctx, instance);
    }
}