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

import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.struts.PMForwardException;

public class EditAction extends RowActionSupport{

	/** Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return true;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return true; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){ 	return true;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }
    
	protected boolean preExecute(PMContext ctx) throws PMException {
		super.preExecute(ctx);
		if(ctx.getRequest().getParameter(FINISH)==null){
			/*This point limite anidation of weak entities.*/
			if(!ctx.isWeak()){
				clearModifiedOwnerCollection(ctx);
			}
			throw new PMForwardException(CONTINUE);
		}
		if(ctx.getSelected() == null){
			ctx.getErrors().add(new PMMessage(ENTITY, "pm.instance.not.found"));
			throw new PMException();
		}
		for (Field f : ctx.getEntity().getFields()) {
        	proccessField(ctx, f, ctx.getSelected());
        }
        if(!ctx.getErrors().isEmpty()) throw new PMException();
		return true;
	}
	
	protected void doExecute(PMContext ctx) throws PMException {
		if(!ctx.isWeak()){
			if(ctx.getEntity().isPersistent())
				ctx.getEntity().getDataAccess().update(ctx, ctx.getSelected().getInstance());
		}
	}
}
