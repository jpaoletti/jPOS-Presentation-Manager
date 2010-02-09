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
import org.hibernate.exception.ConstraintViolationException;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMStrutsException;
import org.jpos.util.NameRegistrar.NotFoundException;

public class AddAction extends RowActionSupport {
	
	
	public boolean testSelectedExist() { return false;	}

	protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		ActionForward af = super.preExecute(rc);
		if(af != null) return af;
		if(rc.getParameter(FINISH)==null){
			//Creates bean and put it in session
			Object obj;
			try {
				obj = getPMService().getFactory().newInstance (rc.getEntity().getClazz());
				rc.getEntity_container().setSelected(new EntityInstanceWrapper(obj));
				rc.getEntity_container().setSelectedNew(true);
				return rc.go();
			} catch (ConfigurationException e) {
				PMLogger.error(e);
				rc.getErrorlist().put(ENTITY, e.getMessage());
				return rc.fail();
			}
		}
		if(rc.getSelected() == null){
			rc.getErrorlist().put(ENTITY, "Entity instance not found");
			return rc.fail();
		}
		for (Field f : rc.getEntity().getFields()) {
        	proccessField(rc, f, rc.getSelected());
        }
        if(!rc.getErrorlist().isEmpty() || !rc.getErrors().isEmpty()) return rc.fail();
		return null;
	}

	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		if(rc.isWeak()){
			getModifiedOwnerCollection(rc, rc.getEntity().getOwner().getEntity_property()).add(rc.getSelected().getInstance());
			String p = rc.getEntity().getOwner().getLocal_property();
			if(p != null){
				rc.getEntitySupport().set(rc.getSelected().getInstance(), p, rc.getOwner().getSelected());
			}
		}else{
			try {
				if(rc.getEntity().isPersistent())
					rc.getDB().session().saveOrUpdate(rc.getSelected().getInstance());
			} catch (ConstraintViolationException e) {
				throw new PMStrutsException("constraint.violation.exception");
			}
		}
		return rc.successful();
	}
	
	protected boolean openTransaction() {
		return true;
	}
}