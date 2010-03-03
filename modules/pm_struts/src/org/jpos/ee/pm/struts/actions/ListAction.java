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
import java.util.Collection;
import java.util.List;

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMList;

public class ListAction extends EntityActionSupport {
	
	/**Makes the operation generate an auditory entry*/
	protected boolean isAudited() {	return false; }
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

	protected void doExecute(PMContext ctx) throws PMException {
		ListActionForm f = (ListActionForm) ctx.getForm();
        configureList(ctx,f);
        boolean searchable = ctx.getOperation().getConfig("searchable", "true").compareTo("true")==0;
        boolean paginable = ctx.getOperation().getConfig("paginable", "true").compareTo("true")==0;
        ctx.getRequest().setAttribute("searchable", searchable);
        ctx.getRequest().setAttribute("paginable", paginable);
	}
	
	private void configureList(PMContext ctx, ListActionForm f) throws PMException {
		//PMListSupport pmls = new PMListSupport();

		List<Object> contents = null;
		long total = 0;
		Integer rpp = 10;
		
		PMList pmlist = ctx.getList();
		if(pmlist == null){
			pmlist = new PMList();
            pmlist.setEntity(ctx.getEntity());
            Operations operations = (Operations) ctx.getSession().getAttribute(OPERATIONS);
            pmlist.setOperations	(operations.getOperationsForScope(SCOPE_GRAL));
            String sortfield = ctx.getOperation().getConfig("sort-field");
            String sortdirection = ctx.getOperation().getConfig("sort-direction");
    		if(sortfield!=null){
    			pmlist.setOrder(sortfield);
    			if(sortdirection!=null && sortdirection.toLowerCase().compareTo("desc")==0)
    				pmlist.setDesc(true);
    		}
		}
		
		if(ctx.getParameter(FINISH)!=null){
			pmlist.setOrder(f.getOrder());
			pmlist.setDesc(f.isDesc());
			pmlist.setPage(f.getPage());
			pmlist.setRowsPerPage(f.getRowsPerPage());
		}
		
		if(ctx.isWeak()){
			//The list is the collection of the owner.
			String entity_property = ctx.getEntity().getOwner().getEntityProperty();
			Collection<Object> moc = getModifiedOwnerCollection(ctx, entity_property);
			ctx.debug("MOC: "+moc);
			if(moc == null){
				moc = getOwnerCollection(ctx);
			}
			ctx.debug("MOC2: "+moc);
			contents = new ArrayList<Object>();
			try {
				Collection<Object> result;
				String collection_class = ctx.getEntity().getOwner().getEntityCollectionClass();
				ctx.debug("Collection Class: "+collection_class);
				result = (Collection<Object>) PMEntitySupport.getInstance().getPmservice().getFactory().newInstance (collection_class );
				if(moc != null)	result.addAll(moc);
				setModifiedOwnerCollection(ctx, entity_property, result);
				contents.addAll(result);
			} catch (ConfigurationException e) {
				PMLogger.error(e);
			}
			total = contents.size();
		}else{
			if(ctx.getEntity().isPersistent()){
				ctx.put(PM_LIST_ORDER, pmlist.getOrder());
				ctx.put(PM_LIST_ASC, !pmlist.isDesc());
				try {
					contents = (List<Object>) ctx.getEntity().getDataAccess().list(ctx, pmlist.from(), pmlist.rpp());
					total = ctx.getEntity().getDataAccess().count(ctx);
				} catch (Exception e) {
					PMLogger.error(e);
					throw new PMException("pm.operation.cant.load.list");
				}
				rpp = pmlist.rpp();
			}else{
				//An empty list that will be filled on an a list context
				contents = new ArrayList<Object>();
			}
		}
		
		ctx.debug("Contents: "+contents);
        ctx.getEntityContainer().setList(pmlist);
        pmlist.setContents(contents);
		pmlist.setTotal(total);
        
        ctx.debug("pmList: "+pmlist);
		pmlist.setRowsPerPage	(rpp);
	}
}
