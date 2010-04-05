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
package org.jpos.ee.pm.struts;

import javax.servlet.http.HttpServletRequest;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntitySupport;

public class PMEntitySupport extends EntitySupport implements Constants{
	private String context_path;
	private static PMEntitySupport instance;
	
	/*Singleton getter*/
	public synchronized static PMEntitySupport getInstance(){
		if(instance == null) instance = new PMEntitySupport();
		return instance;
	}
	
	public EntityContainer getContainer(HttpServletRequest request) {
		String pmid = (String)request.getAttribute(PM_ID);
		return (EntityContainer)request.getSession().getAttribute(pmid);
	}
	
	public Entity putEntityInRequest(HttpServletRequest request) throws PMStrutsException{
		EntityContainer container = getContainer(request);
		if(container==null) throw new PMStrutsException("container.not.found");
		Entity entity = container.getEntity();
		request.setAttribute(ENTITY, entity);
		return entity;
	}

	public PMList putListInRequest(HttpServletRequest request) throws PMStrutsException{
		EntityContainer container = getContainer(request);
		if(container==null) throw new PMStrutsException("container.not.found");
		PMList list = container.getList();
		request.setAttribute(PM_LIST, list);
		return list;
	}

	public Object putItemInRequest(HttpServletRequest request) throws PMStrutsException{
		EntityContainer container = getContainer(request);
		if(container==null) throw new PMStrutsException("container.not.found");
		Object r = container.getSelected().getInstance();
		request.setAttribute(ENTITY_INSTANCE, r);
		return r;
	}
	
	public Object putFilterInRequest(HttpServletRequest request) throws PMStrutsException{
		EntityContainer container = getContainer(request);
		if(container==null) throw new PMStrutsException("container.not.found");
		Object r = container.getFilter().getInstance().getInstance();
		request.setAttribute(ENTITY_INSTANCE, r);
		return r;
	}
	
	
	public void setContext_path(String context_path) {
		this.context_path = context_path;
	}

	public String getContext_path() {
		return context_path;
	}
}
