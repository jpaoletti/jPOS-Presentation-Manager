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

import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class EditOperation extends OperationCommandSupport {

    public EditOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean prepare(PMContext ctx) throws PMException {
        super.prepare(ctx);
        if (ctx.getParameter("finish") != null) {
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
            return true;
        }
        return false;
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        super.doExecute(ctx);
        ctx.getPresentationManager().debug(this, "Updating '" + ctx.getEntity().getId() + "' to Data Access");
        ctx.getEntity().getDataAccess().update(ctx, ctx.getSelected().getInstance());
    }

    @Override
    protected boolean openTransaction() {
        return true;
    }

    @Override
    protected boolean checkUser() {
        return true;
    }

    @Override
    protected boolean checkEntity() {
        return true;
    }
}
