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

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.apache.struts.action.ActionMessages;
import org.hibernate.Transaction;
import org.jpos.ee.DB;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.struts.EntityContainer;
import org.jpos.ee.pm.validator.ValidationResult;
import org.jpos.ee.pm.validator.Validator;

public abstract class EntityActionSupport extends ActionSupport {

    /** Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return false;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return true; }
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

    protected boolean prepare(PMContext ctx) throws PMException {
		super.prepare(ctx);
		
        configureEntityContainer(ctx);
        
		String requrl = ctx.getRequest().getRequestURL().toString();
		String operationId = requrl.substring(requrl.lastIndexOf("/")+1, requrl.lastIndexOf("."));
		ctx.setOperation((ctx.hasEntity())?ctx.getEntity().getOperations().getOperation(operationId):null);
		
		if(ctx.hasEntity() && ctx.getEntity().getExtendz() != null){
			Entity otherentity = getPMService().getEntity(ctx.getEntity().getExtendz()); 
			ctx.getEntity().getFields().addAll(otherentity.getFields());
			ctx.getEntity().setExtendz(null); //we do this one time
		}

		//Its a weak entity, we get the owner entity for list reference
		if(ctx.hasEntity() && ctx.getEntity().getOwner() != null){
			ctx.setOwner(getEntityContainer(ctx,ctx.getEntity().getOwner().getEntity_id()));
			if(ctx.getOwner()== null) {
				throw new PMException("owner.not.exists");
			} 
		}else{
			ctx.setOwner(null);
		}
		
		ctx.getRequest().setAttribute(OPERATION, ctx.getOperation());
        if(ctx.hasEntityContainer())
        	ctx.getSession().setAttribute(OPERATIONS, ctx.getEntity().getOperations().getOperationsFor(ctx.getOperation()));
        //TODO check entity-level permissions
		return true;
	}
    
    protected void excecute(PMContext ctx) throws PMException {
    	
    	if(ctx.getOperation()!= null && ctx.getOperation().getContext()!= null)
    		ctx.getOperation().getContext().preExecute(ctx);
    	
			/* Validate de operation*/
			validate(ctx);
			
			DB db = (DB) ctx.get(DB);
			Transaction tx = null;
			try{
				//If we have audithory we need the transaction.
				if(!getPMService().ignoreDb() && (/*isAuditable(ctx) || */openTransaction())) 
					tx = db.beginTransaction();
	
				/** EXCECUTES THE OPERATION **/
				doExecute(ctx);
	
				if(ctx.getOperation()!= null && ctx.getOperation().getContext()!= null)
					ctx.getOperation().getContext().postExecute(ctx);
			
					/*if(isAuditable(ctx)){
						logRevision (ctx.getDB(), (ctx.getEntity()!=null)?ctx.getEntity().getId():null, ctx.getOper_id(), ctx.getUser());
					}*/
				if(tx != null)tx.commit();
				tx = null;
			}finally{
				if(tx != null)tx.rollback();
			} 
	}

    protected boolean isAuditable(PMContext ctx) throws PMException{
		return isAudited() && ctx.hasEntity() && ctx.getEntity().isAuditable();
	}

	protected boolean configureEntityContainer(PMContext ctx) throws PMException {
		String pmid = ctx.getRequest().getParameter(PM_ID);
		if(pmid==null) {
			pmid=(String) ctx.getSession().getAttribute(LAST_PM_ID);
		}else{
			ctx.getSession().setAttribute(LAST_PM_ID,pmid);
		}
		boolean fail = false;
		ctx.getRequest().setAttribute(PM_ID, pmid);
		if(pmid==null){
        	if(checkEntity()) ctx.getEntityContainer();
        }else{
        	try {
                ctx.setEntityContainer(ctx.getEntityContainer(pmid));
			} catch (PMException e){
				ctx.getErrors().clear();
			}
            if(!ctx.hasEntityContainer()){
            	ctx.setEntityContainer(getPMService().newEntityContainer(pmid));
            	if( checkEntity() ) ctx.getEntityContainer();
            	ctx.getSession().setAttribute(pmid, ctx.getEntityContainer());
            }
        }
        return !fail;
	}
	
	/**
	 * 
	 */
	private void validate(PMContext ctx) throws PMException {
		if(ctx.getOperation()!= null && ctx.getOperation().getValidators()!= null){
		    for (Validator ev : ctx.getOperation().getValidators()) {
		    	ctx.put(PM_ENTITY_INSTANCE, ctx.getSelected().getInstance());
		    	ValidationResult vr = ev.validate(ctx);
		    	ctx.getErrors().addAll(vr.getMessages());
		   	 	if(!vr.isSuccessful()) throw new PMException();
		    }
		}
	}
	
	protected List<Object> getOwnerCollection(PMContext ctx) throws PMException {
		return (List<Object>) ctx.getEntitySupport().get(ctx.getOwner().getSelected().getInstance(), ctx.getEntity().getOwner().getEntity_property());
	}

	protected List<Object> getModifiedOwnerCollection(PMContext ctx, String field) {
		ctx.debug("getModifiedOwnerCollection("+field+")");
		List<Object> collection = (List<Object>) ctx.getSession().getAttribute(field+"_"+MODIFIED_OWNER_COLLECTION);
		/*if(collection == null) {
			collection = new ArrayList<Object>();
			setModifiedOwnerCollection(field, collection);
		}*/
		return collection;
	}
	
	protected void setModifiedOwnerCollection(PMContext ctx, String entity_property, Collection<Object> list) {
		ctx.getSession().setAttribute(entity_property+"_"+MODIFIED_OWNER_COLLECTION, list);
	}

	protected void clearModifiedOwnerCollection(PMContext ctx) {
		Enumeration<String> e = ctx.getSession().getAttributeNames();
		while(e.hasMoreElements()){
			String s = e.nextElement();
			if(s.endsWith(MODIFIED_OWNER_COLLECTION)){
				ctx.getSession().setAttribute(s, null);
			}
		}
	}
	
	protected EntityContainer getEntityContainer(PMContext ctx, String eid) {
		return (EntityContainer) ctx.getRequest().getSession().getAttribute(EntityContainer.buildId(HASH, eid));
	}
}
