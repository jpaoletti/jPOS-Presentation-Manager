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

import java.util.Collection;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;

/**
 *
 * @author jpaoletti
 */
public class DeleteOperation extends OperationCommandSupport {

    public DeleteOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean openTransaction() {
        return true;
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        super.doExecute(ctx);
        final Object instance = ctx.getSelected().getInstance();
        if (ctx.getEntity().isWeak()) {
            final Collection<Object> collection = getOwnerCollection(ctx);
            if (collection != null) {
                collection.remove(instance);
            }
        }
        try {
            ctx.getEntity().getDataAccess().delete(ctx, instance);
        } catch (Exception e) {
            ctx.getPresentationManager().error(e);
            throw new PMException("pm.cant.delete");
        }
        ctx.getEntityContainer().setSelected(null);
    }
}
