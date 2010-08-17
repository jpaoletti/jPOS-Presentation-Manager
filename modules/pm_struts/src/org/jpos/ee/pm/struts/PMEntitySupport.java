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
package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.core.EntityContainer;
import javax.servlet.http.HttpServletRequest;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.PaginatedList;

/**
 * Helper class for internal use.
 * 
 * @author jpaoletti
 * @see EntitySupport
 */
public class PMEntitySupport extends EntitySupport implements Constants{
    private String context_path;
    private static PMEntitySupport instance;
    
    /**
     * Singleton getter
     * @return The PMEntitySupport
     */
    public synchronized static PMEntitySupport getInstance(){
        if(instance == null) instance = new PMEntitySupport();
        return instance;
    }
    
    /**
     * Return the container that is in the given request
     *
     * @param request The request
     * @return The container
     */
    public EntityContainer getContainer(HttpServletRequest request) {
        String pmid = (String)request.getAttribute(PM_ID);
        return (EntityContainer)request.getSession().getAttribute(pmid);
    }
    
    /**
     * Inserts the container entity into the request
     *
     * @param request The request
     * @return The entity
     * @throws PMStrutsException when request has no container
     */
    public Entity putEntityInRequest(HttpServletRequest request) throws PMStrutsException{
        EntityContainer container = getContainer(request);
        if(container==null) throw new PMStrutsException("container.not.found");
        Entity entity = container.getEntity();
        request.setAttribute(ENTITY, entity);
        return entity;
    }

    /**
     * Inserts the container list into the request
     * @param request The request
     * @return The list
     * @throws PMStrutsException when request has no container
     */
    public PaginatedList putListInRequest(HttpServletRequest request) throws PMStrutsException{
        EntityContainer container = getContainer(request);
        if(container==null) throw new PMStrutsException("container.not.found");
        PaginatedList list = container.getList();
        request.setAttribute(PM_LIST, list);
        return list;
    }

    /**
     * Insert the container selected instance into the request
     * @param request The request
     * @return The list
     * @throws PMStrutsException when request has no container
     */
    public Object putItemInRequest(HttpServletRequest request) throws PMStrutsException{
        EntityContainer container = getContainer(request);
        if(container==null) throw new PMStrutsException("container.not.found");
        Object r = container.getSelected().getInstance();
        request.setAttribute(ENTITY_INSTANCE, r);
        return r;
    }
    
    /**
     * Insert the container filter into the request
     * @param request The request
     * @return The list
     * @throws PMStrutsException when request has no container
     */
    public Object putFilterInRequest(HttpServletRequest request) throws PMStrutsException{
        EntityContainer container = getContainer(request);
        if(container==null) throw new PMStrutsException("container.not.found");
        Object r = container.getFilter().getInstance().getInstance();
        request.setAttribute(ENTITY_INSTANCE, r);
        return r;
    }
    
    /**
     * Setter for context path
     *
     * @param context_path The context_path
     */
    public void setContext_path(String context_path) {
        this.context_path = context_path;
    }

    /**
     * Getter for context path
     * 
     * @return The context_path
     */
    public String getContext_path() {
        return context_path;
    }
}
