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

import org.hibernate.exception.ConstraintViolationException;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMForwardException;

public class AddAction extends RowActionSupport {
	
	public boolean testSelectedExist() { return false;	}

	protected boolean prepare(PMContext ctx) throws PMException {
		super.prepare(ctx);
		if(ctx.getParameter(FINISH)==null){
			//Creates bean and put it in session
			Object obj;
			try {
				obj = getPMService().getFactory().newInstance (ctx.getEntity().getClazz());
				ctx.getEntityContainer().setSelected(new EntityInstanceWrapper(obj));
				ctx.getEntityContainer().setSelectedNew(true);
				ctx.debug("Cleaning weak collections");
				if(ctx.getEntity().getWeaks()!=null){
					for(Entity e : ctx.getEntity().getWeaks()){
						setModifiedOwnerCollection(ctx, e.getOwner().getEntityProperty(), null);
					}
				}
				throw new PMForwardException(CONTINUE);
			} catch (ConfigurationException e) {
				PMLogger.error(e);
				throw new PMException("pm_core.unespected.error");
			}
		}
		if(ctx.getSelected() == null){
			throw new PMException("pm.instance.not.found");
		}
		for (Field f : ctx.getEntity().getFields()) {
        	proccessField(ctx, f, ctx.getSelected());
        }
        if(!ctx.getErrors().isEmpty()) 
        	throw new PMException();
        
        return true;
	}

	protected void doExecute(PMContext ctx) throws PMException {
		Object instance = ctx.getSelected().getInstance();
		if(ctx.isWeak()){
			getModifiedOwnerCollection(ctx, ctx.getEntity().getOwner().getEntityProperty()).add(instance);
			String p = ctx.getEntity().getOwner().getLocalProperty();
			if(p != null){
				EntitySupport.set(instance, p, ctx.getOwner().getSelected().getInstance());
			}
		}else{
			try {
				if(ctx.getEntity().isPersistent()){
					ctx.debug("Saving '"+ctx.getEntity().getId()+"' to Data Access");
					ctx.getEntity().getDataAccess().add(ctx, instance);
				}
			} catch (ConstraintViolationException e) {
				throw new PMException("constraint.violation.exception");
			}
		}
	}
	
	protected boolean openTransaction() {
		return true;
	}
}