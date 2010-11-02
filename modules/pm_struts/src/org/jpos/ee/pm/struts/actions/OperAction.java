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

import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class OperAction extends EntityActionSupport{
    
    /**Opens an DB transaction before doExecute*/
    @Override
    protected boolean openTransaction() { return false;    }
    /**Makes the operation generate an audithory entry*/
    @Override
    protected boolean isAudited() {    return false; }
    /**Forces execute to check if any user is logged in*/
    @Override
    protected boolean checkUser(){     return true;}
    /**Forces execute to check if there is an entity defined in parameters*/
    @Override
    protected boolean checkEntity(){ return true; }

    @Override
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
    	Operations operations;
        configureEntityContainer(ctx); 
        ctx.setOperation ( ctx.getEntity().getOperations().getOperation("list") );
        ctx.getRequest().setAttribute(OPERATION, ctx.getOperation());
        operations = ctx.getEntity().getOperations().getOperationsFor(ctx.getOperation());
        ctx.getRequest().setAttribute(ITEM_OPERATIONS, operations.getOperationsForScope(SCOPE_ITEM));
        return true;
    }
    
    protected void doExecute(PMStrutsContext ctx)throws PMException{}
}