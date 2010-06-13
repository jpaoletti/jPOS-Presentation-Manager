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

import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class DeleteAction extends RowActionSupport {

    protected boolean openTransaction() {return true;}

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        if(ctx.isWeak()){
            getModifiedOwnerCollection(ctx, ctx.getEntity().getOwner().getEntityProperty()).remove(ctx.getSelected().getInstance());
        }else{
            try {
                ctx.getEntity().getDataAccess().delete(ctx, ctx.getSelected().getInstance());
            } catch (Exception e) {
                PMLogger.error(e);
                throw new PMException("pm.struts.cant.delete");
            }
        }
        ctx.getEntityContainer().setSelected(null);
    }

}
