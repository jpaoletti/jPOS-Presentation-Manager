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
package org.jpos.ee.pm.struts.actions;

import org.apache.struts.action.ActionForward;
import org.jpos.ee.pm.core.Operations;
import org.jpos.util.NameRegistrar.NotFoundException;

public class OperAction extends EntityActionSupport{
	Operations operations;
	
    /**Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return false;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return false; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){ 	return true;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

    protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
    	ActionForward r = null; //super.preExecute(rc);
    	if(!configureEntityContainer(rc))return rc.fail();
    	rc.setOperation ( rc.getEntity().getOperations().getOperation("list") );
    	rc.getRequest().setAttribute(OPERATION, rc.getOperation());
        operations = rc.getEntity().getOperations().getOperationsFor(rc.getOperation());
        return r;
	}
	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		rc.getRequest().setAttribute(ITEM_OPERATIONS, operations.getOperationsForScope(SCOPE_ITEM));
		return rc.successful();
	}
}