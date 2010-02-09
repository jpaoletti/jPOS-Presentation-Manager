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

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Transaction;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.EntityContainer;
import org.jpos.ee.pm.validator.ValidationResult;
import org.jpos.ee.pm.validator.Validator;
import org.jpos.util.NameRegistrar.NotFoundException;

public abstract class EntityActionSupport extends ActionSupport {

    /** Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return false;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return true; }
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

    protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		ActionForward r = super.preExecute(rc);
		if(r != null) return r;
        
        if(!configureEntityContainer(rc))return rc.fail();
        
		String requrl = rc.getRequest().getRequestURL().toString();
		rc.setOper_id(requrl.substring(requrl.lastIndexOf("/")+1, requrl.lastIndexOf(".")));
		rc.setOperation((rc.getEntity()!=null)?rc.getEntity().getOperations().getOperation(rc.getOper_id()):null);
		
		if(rc.getEntity() != null && rc.getEntity().getExtendz() != null){
			Entity otherentity = getPMService().getEntity(rc.getEntity().getExtendz()); 
			rc.getEntity().getFields().addAll(otherentity.getFields());
			rc.getEntity().setExtendz(null); //we do this one time
		}

		//Its a weak entity, we get the owner entity for list reference
		if(rc.getEntity() != null && rc.getEntity().getOwner() != null){
			rc.setOwner(getEntityContainer(rc,rc.getEntity().getOwner().getEntity_id()));
			if(rc.getOwner()== null) {
				rc.getErrors().add(ENTITY, new ActionMessage("owner.not.exists"));
				return rc.fail();
			} 
		}else{
			rc.setOwner(null);
		}
		
		rc.getRequest().setAttribute(OPERATION, rc.getOperation());
        if(rc.getEntity_container() != null)
        	rc.getSession().setAttribute(OPERATIONS, rc.getEntity().getOperations().getOperationsFor(rc.getOperation()));
        //TODO check entity-level permissions
		return null;
	}
    
    protected ActionForward excecute(RequestContainer rc, ActionForward r) throws Exception {
    	
    	if(rc.getOperation()!= null && rc.getOperation().getContext()!= null)
    		rc.getOperation().getContext().preExecute(rc.getDB(), rc.getUser(), rc.getEntity_container(), rc.getSelected());
    	
		if(r == null){
			/* Validate de operation*/
			if(validate(rc)) {
				Transaction tx = null;
				//If we have audithory we need the transaction.
				if(!getPMService().ignoreDb() && (isAuditable(rc) || openTransaction())) 
					tx = rc.getDB().beginTransaction();
		        try {
		        	/** EXCECUTES THE OPERATION **/
					r = doExecute(rc);

					if(r == rc.successful()){
						if(rc.getOperation()!= null && rc.getOperation().getContext()!= null)
							rc.getOperation().getContext().postExecute(rc.getDB(), rc.getUser(), rc.getEntity_container(), rc.getSelected());
		
						if(isAuditable(rc)){
							logRevision (rc.getDB(), (rc.getEntity()!=null)?rc.getEntity().getId():null, rc.getOper_id(), rc.getUser());
						}
						if(tx != null)tx.commit();
						r = rc.successful();
					}else{
						if(tx != null) tx.rollback();
						return r;
					}
				} catch (PMException e) {
					PMLogger.error(e);
					if(tx != null) tx.rollback();
					rc.getErrors().add(ENTITY, new ActionMessage(e.getKey()));
					return rc.fail();
				} catch (Exception e) {
					PMLogger.error(e);
					if(tx != null) tx.rollback();
					rc.getErrorlist().put(ENTITY, e.getMessage());
					return rc.fail();
				}
			}else
				r =  rc.fail(); 
		}		
		return r;
	}

    
    
	protected boolean isAuditable(RequestContainer rc){
		return isAudited() && rc.getEntity()!= null && rc.getEntity().isAuditable();
	}

	protected boolean configureEntityContainer(RequestContainer rc) throws NotFoundException {
		String pmid = rc.getRequest().getParameter(PM_ID);
		if(pmid==null) {
			pmid=(String) rc.getSession().getAttribute(LAST_PM_ID);
		}else{
			rc.getSession().setAttribute(LAST_PM_ID,pmid);
		}
		boolean fail = false;
		rc.getRequest().setAttribute(PM_ID, pmid);
        ActionMessage amue = new ActionMessage("unknow.entity",pmid);
		if(pmid==null){
        	if(checkEntity()) {
        		rc.getErrors().add(ENTITY,amue);
        		fail= true;
        	}
        }else{
            rc.setEntity_container(rc.getEntityContainer(pmid));
            if(rc.getEntity_container() == null){
            	debug("Configurando container "+ pmid);
            	rc.setEntity_container(getPMService().newEntityContainer(pmid));
            	if(rc.getEntity_container() == null && checkEntity()) {
            		rc.getErrors().add(ENTITY,amue);
            		fail= true;
            	}
            	rc.getSession().setAttribute(pmid, rc.getEntity_container());
            }
        }
        return !fail;
	}
	/**
	 * 
	 */
	private boolean validate(RequestContainer rc) {
		if(rc.getOperation()!= null && rc.getOperation().getValidators()!= null){
		    for (Validator ev : rc.getOperation().getValidators()) {
		    	ev.setDb(rc.getDB());
		    	ValidationResult vr = ev.validate(rc.getEntity(),null,rc.getSelected().getInstance(),null);
		    	rc.getErrorlist().putAll(vr.getMessages());
		   	 	if(!vr.isSuccessful()) return false;
		    }
		}
		return true;
	}
	
	protected List<Object> getOwnerCollection(RequestContainer rc) {
		return (List<Object>) rc.getEntitySupport().get(rc.getOwner().getSelected().getInstance(), rc.getEntity().getOwner().getEntity_property());
	}

	protected List<Object> getModifiedOwnerCollection(RequestContainer rc, String field) {
		debug("getModifiedOwnerCollection("+field+")");
		List<Object> collection = (List<Object>) rc.getSession().getAttribute(field+"_"+MODIFIED_OWNER_COLLECTION);
		/*if(collection == null) {
			collection = new ArrayList<Object>();
			setModifiedOwnerCollection(field, collection);
		}*/
		return collection;
	}
	
	protected void setModifiedOwnerCollection(RequestContainer rc, String entity_property, Collection<Object> list) {
		debug("setModifiedOwnerCollection("+entity_property+", "+list+")");
		rc.getSession().setAttribute(entity_property+"_"+MODIFIED_OWNER_COLLECTION, list);
	}

	protected void clearModifiedOwnerCollection(RequestContainer rc) {
		Enumeration<String> e = rc.getSession().getAttributeNames();
		while(e.hasMoreElements()){
			String s = e.nextElement();
			if(s.endsWith(MODIFIED_OWNER_COLLECTION)){
				rc.getSession().setAttribute(s, null);
			}
		}
	}
	
	protected EntityContainer getEntityContainer(RequestContainer rc, String eid) {
		return (EntityContainer) rc.getRequest().getSession().getAttribute(EntityContainer.buildId(HASH, eid));
	}
}
