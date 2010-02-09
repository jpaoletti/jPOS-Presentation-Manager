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

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForward;
import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMListSupport;

public class ListAction extends EntityActionSupport {
	
	/**Makes the operation generate an auditory entry*/
	protected boolean isAudited() {	return false; }
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		ListActionForm f = (ListActionForm) rc.getForm();
        configureList(rc,f);
        boolean searchable = rc.getOperation().getConfig("searchable", "true").compareTo("true")==0;
        boolean paginable = rc.getOperation().getConfig("paginable", "true").compareTo("true")==0;
        rc.getRequest().setAttribute("searchable", searchable);
        rc.getRequest().setAttribute("paginable", paginable);
		return rc.successful();
	}
	private void configureList(RequestContainer rc, ListActionForm f) throws ClassNotFoundException {
		PMListSupport pmls = new PMListSupport(rc.getDbs());

		List<Object> contents = null;
		Integer total = 0;
		Integer rpp = 10;
		
		PMList pmlist = rc.getList();
		if(pmlist == null){
			pmlist = new PMList();
            pmlist.setEntity(rc.getEntity());
            Operations operations = (Operations) rc.getSession().getAttribute(OPERATIONS);
            pmlist.setOperations	(operations.getOperationsForScope(SCOPE_GRAL));
            String sortfield = rc.getOperation().getConfig("sort-field");
            String sortdirection = rc.getOperation().getConfig("sort-direction");
    		if(sortfield!=null){
    			pmlist.setOrder(sortfield);
    			if(sortdirection!=null && sortdirection.toLowerCase().compareTo("desc")==0)
    				pmlist.setDesc(true);
    		}
		}
		
		if(rc.getParameter(FINISH)!=null){
			pmlist.setOrder(f.getOrder());
			pmlist.setDesc(f.isDesc());
			pmlist.setPage(f.getPage());
			pmlist.setRowsPerPage(f.getRowsPerPage());
		}
		
		if(rc.isWeak()){
			//The list is the collection of the owner.
			String entity_property = rc.getEntity().getOwner().getEntity_property();
			List<Object> moc = getModifiedOwnerCollection(rc, entity_property);
			if(moc == null){
				moc = getOwnerCollection(rc);
			}
			if(moc != null)
				contents = moc;
			else
				contents = new ArrayList<Object>();
			setModifiedOwnerCollection(rc, entity_property, contents);
			total = contents.size();
		}else
		if(rc.getEntity().isPersistent()){
			//we must get the list form the DB
	        contents= pmls.getContentList(rc.getEntity(),rc.getEntity_container().getFilter(), pmlist);
	    	total= pmls.getTotal();
	    	rpp= pmls.getRpp();
		}else{
			//An empty list that will be filled on an a list context
			contents = new ArrayList<Object>();
		}
		
		debug("Contents: "+contents);
        //if(pmList == null || !pmList.getEntity().equals(rc.getEntity())){
        //pmList = new PMList(contents,total);
        rc.getEntity_container().setList(pmlist);
        pmlist.setContents(contents);
		pmlist.setTotal(total);
        
        debug("pmList: "+pmlist);
		pmlist.setRowsPerPage	(rpp);
	}
}
