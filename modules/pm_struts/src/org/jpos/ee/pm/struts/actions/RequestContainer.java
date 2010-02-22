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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jpos.ee.Constants;
import org.jpos.ee.DB;
import org.jpos.ee.DBSupport;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.struts.EntityContainer;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMStrutsService;

/**@deprecated*/
public class RequestContainer implements Constants{
    private ActionMapping mapping;
    private ActionForm form;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String,String> errorlist;
    private DBSupport dbs;
    private ActionErrors errors;
    private EntityContainer entity_container;
    private Operation operation;
    private EntityContainer owner = null;
    private String oper_id; 
    
	/**
	 * @return the mapping
	 */
	public ActionMapping getMapping() {
		return mapping;
	}
	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(ActionMapping mapping) {
		this.mapping = mapping;
	}
	/**
	 * @return the form
	 */
	public ActionForm getForm() {
		return form;
	}
	/**
	 * @param form the form to set
	 */
	public void setForm(ActionForm form) {
		this.form = form;
	}
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	/**
	 * @return the errorlist
	 */
	public Map<String, String> getErrorlist() {
		return errorlist;
	}
	/**
	 * @param errorlist the errorlist to set
	 */
	public void setErrorlist(Map<String, String> errorlist) {
		this.errorlist = errorlist;
	}
	/**
	 * @return the dbs
	 */
	public DBSupport getDbs() {
		return dbs;
	}
	/**
	 * @param dbs the dbs to set
	 */
	public void setDbs(DBSupport dbs) {
		this.dbs = dbs;
	}
	/**
	 * @return the errors
	 */
	public ActionErrors getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}
	
	public HttpSession getSession(){
		return getRequest().getSession();
	}
	
	public PMEntitySupport getEntitySupport(){		
		return (PMEntitySupport)getRequest().getSession().getAttribute(ENTITY_SUPPORT);	
	}		
	
	public DB getDB(){
		if(getPMService().ignoreDb()) return null;
		DB db = (DB)getSession().getAttribute(DB);
		if(db == null) {
			PMLogger.info("Database Access Created");
			db = new DB(PMLogger.getLog());
			db.open();
			getSession().setAttribute(DB, db);
		}
		return db;
	}
	
    
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
	
	public ActionForward fail(Exception e) {
		e.printStackTrace();
		PMLogger.error(e);
		getErrorlist().put(ENTITY, e.getMessage());
		return fail();
	}
	
	public ActionForward fail() {
		for (Map.Entry<String,String> me : errorlist.entrySet()) {
        	errors.add(me.getKey(), new ActionMessage("message",me.getValue()));
        }
		return getMapping().findForward(FAILURE);
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
	 * @param entity_container the entity_container to set
	 */
	public void setEntity_container(EntityContainer entity_container) {
		this.entity_container = entity_container;
	}
	/**
	 * @return the entity_container
	 */
	public EntityContainer getEntity_container() {
		return entity_container;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EntityContainer owner) {
		this.owner = owner;
	}
	/**
	 * @return the owner
	 */
	public EntityContainer getOwner() {
		return owner;
	}
		
	public Entity getEntity(){
    	if(getEntity_container() == null ) return null;
    	return getEntity_container().getEntity();
    }
	
	protected PMList getList() {
		return getEntity_container().getList();
	}
	
	public boolean isWeak(){
		return getOwner() != null;
	}

	public EntityInstanceWrapper getSelected() {
		return getEntity_container().getSelected();
	}
	
}