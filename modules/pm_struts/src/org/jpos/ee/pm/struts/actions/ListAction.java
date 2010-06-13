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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.util.DisplacedList;

public class ListAction extends EntityActionSupport {
    
    /**Makes the operation generate an auditory entry*/
    protected boolean isAudited() {    return false; }
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }
    
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
    	super.prepare(ctx);
    	PMLogger.debug(this, "Prepare");
        return true;
    }
	private void prepareParameters(PMStrutsContext ctx) throws PMException {
		
		boolean searchable = ctx.getOperation().getConfig("searchable", "true").compareTo("true")==0;
        boolean paginable = ctx.getOperation().getConfig("paginable", "true").compareTo("true")==0;
        Boolean showRowNumber = ctx.getOperation().getConfig("show-row-number", "false").compareTo("true")==0;
        String operationColWidth= ctx.getOperation().getConfig("operation-column-width", "50px");
        Operations operations = (Operations) ctx.getSession().getAttribute(OPERATIONS);
        
        ctx.getList().setSearchable(searchable);
        ctx.getList().setPaginable(paginable);
        ctx.getList().setShowRowNumber(showRowNumber);
        ctx.getList().setOperationColWidth(operationColWidth);
        ctx.getList().setHasSelectedScope(operations.getOperationsForScope(SCOPE_SELECTED).count() > 0);
        
        //ctx.getRequest().setAttribute("searchable", searchable);
        //ctx.getRequest().setAttribute("paginable", paginable);
        //ctx.getRequest().setAttribute("show_row_number", showRowNumber);
        //ctx.getRequest().setAttribute("operation_column_width", operationColWidth);
        //ctx.getRequest().setAttribute("has_selected",operations.getOperationsForScope(SCOPE_SELECTED).count() > 0 );
	}

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        ListActionForm f = (ListActionForm) ctx.getForm();
        configureList(ctx,f);
        prepareParameters(ctx);
    }
    
    private void configureList(PMStrutsContext ctx, ListActionForm f) throws PMException {
        List<Object> contents = null;
        Long total = null;
        Integer rpp = 10;
        
        PaginatedList pmlist = ctx.getList();
        if(pmlist == null){
            pmlist = new PaginatedList();
            pmlist.setEntity(ctx.getEntity());
            Operations operations = (Operations) ctx.getSession().getAttribute(OPERATIONS);
            pmlist.setOperations    (operations.getOperationsForScope(SCOPE_GRAL, SCOPE_SELECTED));
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
            pmlist.setPage((f.getPage()!=null && f.getPage()>0)?f.getPage():1);
            pmlist.setRowsPerPage(f.getRowsPerPage());
        }
        
        if(ctx.isWeak()){
            PMLogger.debug(this,"Listing weak entity");
            //The list is the collection of the owner.
            String entity_property = ctx.getEntity().getOwner().getEntityProperty();
            Collection<Object> moc = getModifiedOwnerCollection(ctx, entity_property);
            if(moc == null){
                moc = getOwnerCollection(ctx);
            }
            contents = new ArrayList<Object>();
            try {
                Collection<Object> result;
                String collection_class = ctx.getEntity().getOwner().getEntityCollectionClass();
                result = (Collection<Object>) PMEntitySupport.getInstance().getPmservice().getFactory().newInstance (collection_class );
                if(moc != null)    result.addAll(moc);
                PMLogger.debug(this,"Setting modified owner collection: "+result);
                setModifiedOwnerCollection(ctx, entity_property, result);
                contents.addAll(result);
            } catch (ConfigurationException e) {
                PMLogger.error(e);
            }
            if(!ctx.getEntity().getNoCount())
            	total = new Long(contents.size());
        }else{
            ctx.put(PM_LIST_ORDER, pmlist.getOrder());
            ctx.put(PM_LIST_ASC, !pmlist.isDesc());
            try {
                contents = (List<Object>) ctx.getEntity().getList(ctx, ctx.getEntityContainer().getFilter(), pmlist.from(), pmlist.rpp());
                if(!ctx.getEntity().getNoCount())
                	total = ctx.getEntity().getDataAccess().count(ctx);
            } catch (Exception e) {
                PMLogger.error(e);
                throw new PMException("pm.operation.cant.load.list");
            }
            rpp = pmlist.rpp();
        }
        
        PMLogger.debug(this,"List Contents: "+contents);
        ctx.getEntityContainer().setList(pmlist);
        pmlist.setContents(new DisplacedList<Object>( contents ));
        pmlist.setTotal(total);
        
        PMLogger.debug(this,"Resulting list: "+pmlist);
        pmlist.setRowsPerPage    (rpp);
    }
}
