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
package org.jpos.ee.pm.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jpos.ee.Constants;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.struts.EntityContainer;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMStrutsService;
import org.jpos.transaction.Context;

/**An extension of the org.jpos.transaction.Context class with some helpers
 * for PM.*/
public class PMContext extends Context implements Constants{
	
	/**
	 * @return the mapping
	 */
	public ActionMapping getMapping() {
		return (ActionMapping)get(PM_MAPPINGS);
	}
	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(ActionMapping mapping) {
		put(PM_MAPPINGS, mapping);
	}
	/**
	 * @return the form
	 */
	public ActionForm getForm() {
		return (ActionForm) get(PM_ACTION_FORM);
	}
	/**
	 * @param form the form to set
	 */
	public void setForm(ActionForm form) {
		put(PM_ACTION_FORM, form);
	}
	
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest)get(PM_HTTP_REQUEST);
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		put(PM_HTTP_REQUEST, request);
	}
	
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) get(PM_HTTP_RESPONSE);
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		put(PM_HTTP_RESPONSE,response);
	}

	/**
	 * @return the errors
	 */
	public List<PMMessage> getErrors() {
		return (List<PMMessage>) get(PM_ERRORS);
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<PMMessage> errors) {
		put(PM_ERRORS,errors);
	}
	
	public HttpSession getSession(){
		return getRequest().getSession();
	}
	
	public PMEntitySupport getEntitySupport(){		
		return (PMEntitySupport)getRequest().getSession().getAttribute(ENTITY_SUPPORT);	
	}		
	
	/*public DB getDB(){
		if(getPMService().ignoreDb()) return null;
		DB db = (DB)getSession().getAttribute(DB);
		if(db == null) {
			PMLogger.info("Database Access Created");
			db = new DB(PMLogger.getLog());
			db.open();
			getSession().setAttribute(DB, db);
		}
		return db;
	}*/
	
    
	public PMSecurityUser getUser(){
		PMSecurityUser user = (PMSecurityUser) getSession().getAttribute(USER);
    	return user;
    }
    
	public boolean isUserOnLine() {
		return (getUser() != null);
	}
	
	/* ActionForwards Helpers */
	public ActionForward successful() {
		return getMapping().findForward(SUCCESS);
	}
	
	public ActionForward go() {
		return getMapping().findForward(CONTINUE);
	}

	public ActionForward deny() {
		return getMapping().findForward(DENIED);
	}
	
	public EntityContainer getEntityContainer(String id){
		return (EntityContainer) getSession().getAttribute(id);
	}
	public String getParameter(String s) {
		return getRequest().getParameter(s);
	}

    protected PMStrutsService getPMService(){    	
    	return (PMStrutsService) PMEntitySupport.staticPmservice();
    }
	/**
	 * @param entityContainer the entity_container to set
	 */
	public void setEntityContainer(EntityContainer entityContainer) {
		put(PM_ENTITY_CONTAINER,entityContainer);
	}
	/**
	 * @return the entity_container
	 */
	public EntityContainer getEntityContainer() {
		return (EntityContainer) get(PM_ENTITY_CONTAINER);
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Operation operation) {
		put(PM_OPERATION,operation);
	}
	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return (Operation)get(PM_OPERATION);
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EntityContainer owner) {
		put(PM_OWNER,owner);
	}
	/**
	 * @return the owner
	 */
	public EntityContainer getOwner() {
		return (EntityContainer)get(PM_OWNER);
	}
	
	public Entity getEntity(){
    	if(getEntityContainer() == null ) return null;
    	return getEntityContainer().getEntity();
    }
	
	public PMList getList() {
		return getEntityContainer().getList();
	}
	
	public boolean isWeak(){
		return getOwner() != null;
	}

	public EntityInstanceWrapper getSelected() {
		return getEntityContainer().getSelected();
	}
	
	public void debug(Object object){
		System.out.println("["+this.getClass().getName()+"] DEBUG: "+object);
	}

}