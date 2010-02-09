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
import org.jpos.ee.pm.core.Field;
import org.jpos.util.NameRegistrar.NotFoundException;

public class EditAction extends RowActionSupport{

	/** Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return true;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return true; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){ 	return true;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }
    
	protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		ActionForward af = super.preExecute(rc);
		if(af != null) return af;
		if(rc.getRequest().getParameter(FINISH)==null){
			/*This point limite anidation of weak entities.*/
			if(!rc.isWeak()){
				clearModifiedOwnerCollection(rc);
			}
			return rc.go();
		}
		if(rc.getSelected() == null){
			rc.getErrorlist().put(ENTITY, "Entity instance not found");
			return rc.fail();
		}
		for (Field f : rc.getEntity().getFields()) {
        	proccessField(rc, f, rc.getSelected());
        }
        if(!rc.getErrorlist().isEmpty()) return rc.fail();
		return null;
	}
	
	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		if(rc.isWeak()){
			
		}else{
			if(rc.getEntity().isPersistent())
				rc.getDB().session().update(rc.getSelected().getInstance());
		}
		return rc.successful();
	}
}
