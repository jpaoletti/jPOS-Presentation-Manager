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
package org.jpos.ee.pm.core.operations;

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class AddOperation extends OperationCommandSupport {

    public AddOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean prepare(PMContext ctx) throws PMException {
        super.prepare(ctx);
        if (ctx.getParameter("finish") == null) {
            //Creates bean and put it in session
            Object obj;
            try {
                obj = getPMService().getFactory().newInstance(ctx.getEntity().getClazz());
                ctx.getEntityContainer().setSelected(new EntityInstanceWrapper(obj));
                ctx.getEntityContainer().setSelectedNew(true);
                return false;
            } catch (ConfigurationException e) {
                ctx.getPresentationManager().error(e);
                throw new PMException("pm_core.unespected.error");
            }
        } else {
            if (ctx.getSelected() == null) {
                throw new PMException("pm.instance.not.found");
            }
            for (Field f : ctx.getEntity().getAllFields()) {
                if (f.shouldDisplay(ctx.getOperation().getId())) {
                    proccessField(ctx, f, ctx.getSelected());
                }
            }
            if (!ctx.getErrors().isEmpty()) {
                throw new PMException();
            }

            if (ctx.getEntity().isWeak()) {
                final Object parent = ctx.getEntityContainer().getOwner().getSelected().getInstance();
                final EntityOwner owner = ctx.getEntity().getOwner();
                final Object instance = ctx.getSelected().getInstance();
                ctx.getPresentationManager().set(instance, owner.getLocalProperty(), parent);
                getOwnerCollection(ctx).add(instance);
            }
        }
        return true;
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        Object instance = ctx.getSelected().getInstance();
        ctx.getPresentationManager().debug(this, "Saving '" + ctx.getEntity().getId() + "' to Data Access");
        ctx.getEntity().getDataAccess().add(ctx, instance);
    }

    @Override
    public boolean checkSelected() {
        return false;
    }

    @Override
    protected boolean openTransaction() {
        return true;
    }
}
