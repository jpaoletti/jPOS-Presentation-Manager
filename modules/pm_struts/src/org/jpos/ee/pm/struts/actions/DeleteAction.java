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

public class DeleteAction extends RowActionSupport {

	protected boolean openTransaction() {return true;}

	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		if(rc.isWeak()){
			getModifiedOwnerCollection(rc, rc.getEntity().getOwner().getEntity_property()).remove(rc.getSelected().getInstance());
		}else{
			if(rc.getEntity().isPersistent())
				rc.getDB().session().delete(rc.getSelected().getInstance());
		}
		rc.getEntity_container().setSelected(null);
		return rc.successful();
	}
}
