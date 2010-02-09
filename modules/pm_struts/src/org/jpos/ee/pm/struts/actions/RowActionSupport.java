/*
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

import java.util.List;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMListSupport;
import org.jpos.util.NameRegistrar.NotFoundException;

public abstract class RowActionSupport extends FieldProcessingActionSupport {
	
	public boolean testSelectedExist(){ return true; }

	protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		ActionForward r = super.preExecute(rc);
		
		try {
			//If we get item param, we change the selected item on the container
			String item = rc.getParameter("item");
			if(item!=null && item.trim().compareTo("") != 0){
				Integer index = Integer.parseInt(item);
				debug("Row index: "+ index);
				if(index != null){
					List <Object> al;
					if(rc.isWeak())
						al = getModifiedOwnerCollection(rc, rc.getEntity().getOwner().getEntity_property());
					else
						al = rc.getList().getContents();
					rc.getEntity_container().setSelected(new EntityInstanceWrapper(al.get(index)));
				}
			}else{
				String identified = rc.getParameter("identified");
				if(identified!=null && identified.trim().compareTo("") != 0){
					debug("Row identified by: "+identified);
					String[] ss = identified.split(":");
					//TODO Throw exception when the size of this is not 2
					if(ss.length != 2) debug("Ivalid row identifier!");
					else{
						String prop = ss[0];
						String value= ss[1];
						PMListSupport pmls = new PMListSupport(rc.getDbs());
						rc.getEntity_container().setSelected(new EntityInstanceWrapper(pmls.getObject(rc.getEntity_container().getEntity(), prop, value)));
					}
				}else{
					debug("Row Selection ignored");
				}
			}
		} catch (Exception e) {
			PMLogger.error(e);
			return rc.fail();
		}
		
		if(r == null){
			if(testSelectedExist() && rc.getEntity_container().getSelected() == null){
				rc.getErrors().add(ENTITY,new ActionMessage("unknow.item"));
		       	return rc.fail();
			}else
				return null;
		}else return r;
	}	
}