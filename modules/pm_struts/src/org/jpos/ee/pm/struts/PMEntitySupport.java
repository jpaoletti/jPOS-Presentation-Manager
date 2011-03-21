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

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.PMSession;
import org.jpos.ee.pm.core.PaginatedList;

/**
 * Helper class for internal use.
 * 
 * @author jpaoletti
 * @see EntitySupport
 */
public class PMEntitySupport extends EntitySupport {

    public static final String PMSESSION = "pmsession";
    public static String PM_ID = "pmid";
    public static final String LAST_PM_ID = "last_pmid";
    private String context_path;
    private static PMEntitySupport instance;
    private HttpServletRequest request;

    /**
     * Singleton getter
     * @return The PMEntitySupport
     */
    public synchronized static PMEntitySupport getInstance() {
        if (instance == null) {
            instance = new PMEntitySupport();
        }
        return instance;
    }

    /**
     * Return the container that is in the given request
     *
     * @param request The request
     * @return The container
     */
    public EntityContainer getContainer() throws PMStrutsException {
        if (request == null) {
            throw new PMStrutsException("request.not.found");
        }
        String pmid = (String) request.getAttribute(PM_ID);
        return getPMSession().getContainer(pmid);
    }

    public PMSession getPMSession() throws PMStrutsException {
        if (request == null) {
            throw new PMStrutsException("request.not.found");
        }
        return (PMSession) request.getSession().getAttribute(PMSESSION);
    }

    /**
     * Inserts the container entity into the request
     *
     * @param request The request
     * @return The entity
     * @throws PMStrutsException when the request was not setted
     */
    public Entity getEntity() throws PMStrutsException {
        EntityContainer container = getContainer();
        if (container != null) {
            return container.getEntity();
        }
        return null;
    }

    /**
     * Inserts the container list into the request
     * @param request The request
     * @return The list
     * @throws PMStrutsException when request has no container
     */
    public PaginatedList getList() throws PMStrutsException {
        EntityContainer container = getContainer();
        if (container == null) {
            throw new PMStrutsException("container.not.found");
        }
        PaginatedList list = container.getList();
        return list;
    }

    /**
     * Insert the container selected instance into the request
     * @param request The request
     * @return The list
     * @throws PMStrutsException when request has no container
     */
    public Object getSelected() throws PMStrutsException {
        EntityContainer container = getContainer();
        if (container == null) {
            throw new PMStrutsException("container.not.found");
        }
        Object r = container.getSelected().getInstance();
        return r;
    }

    /**
     * Returns the filter applied
     * 
     * @return The filter
     * @throws PMStrutsException when request has no container
     */
    public EntityFilter getFilter() throws PMStrutsException {
        EntityContainer container = getContainer();
        if (container == null) {
            throw new PMStrutsException("container.not.found");
        }
        return container.getFilter();
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

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Integer getListTotalDigits() {
        try {
            return (getList().getTotal() == null || getList().getTotal() == 0) ? 1 : (int) Math.log10(getList().getTotal()) + 1;
        } catch (PMStrutsException ex) {
            return 0;
        }
    }

    public String getNavigationList(final EntityContainer container) {
        final StringBuilder sb = new StringBuilder();
        if (container != null) {
            sb.append(getNavigationList(container.getOwner()));
            sb.append("&nbsp; &gt; &nbsp;");
            sb.append("<a href='");
            sb.append(getContext_path());
            sb.append("/");
            sb.append(container.getOperation().getId());
            sb.append(".do?pmid=");
            sb.append(container.getEntity().getId()).append(" >");
            sb.append(container.getSelected().getInstance());
            sb.append("</a>");
        }
        return sb.toString();
    }
}
