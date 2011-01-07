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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class GeneralFilter implements Filter, Constants {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute("pm", PresentationManager.pm);
        if (PresentationManager.pm == null) {
            chain.doFilter(request, response);
            return;
        }
        PMEntitySupport o = (PMEntitySupport) req.getSession().getAttribute(ENTITY_SUPPORT);
        if (o == null) {
            PMEntitySupport es = PMEntitySupport.getInstance();
            es.setContext_path(req.getContextPath());
            req.getSession().setAttribute(ENTITY_SUPPORT, es);
        }
        PMStrutsContext ctx = new PMStrutsContext(req.getSession().getId());
        req.setAttribute("ctx", ctx);
        ctx.setRequest(req);
        ctx.setResponse((HttpServletResponse) response);
        ctx.setErrors(new ArrayList<PMMessage>());
        ctx.getRequest().setAttribute(PM_CONTEXT, ctx);
        ctx.put(ActionSupport.USER, ctx.getSession().getAttribute(ActionSupport.USER));

        for (Object object : req.getParameterMap().entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            ctx.put("param_" + entry.getKey(), entry.getValue());
        }
        
        final Object pmid = ctx.getParameter("pmid");
        ctx.put(OperationCommandSupport.PM_ID, pmid);
        ctx.getRequest().setAttribute("pmid", pmid);

        final Object item = ctx.getParameter("item");
        ctx.put(OperationCommandSupport.PM_ITEM, item);
        ctx.getRequest().setAttribute("item", item);


        try {
            ctx.getPresentationManager().getPersistenceManager().init(ctx);

            chain.doFilter(request, response);
        } catch (ServletException e) {
            error(ctx, e);
            throw e;
        } catch (Exception e) {
            error(ctx, e);
        } finally {
            try {
                ctx.getPresentationManager().getPersistenceManager().finish(ctx);
            } catch (Exception e) {
                error(ctx, e);
            }
        }
    }

    protected void error(PMStrutsContext ctx, Exception e) {
        if (ctx.getPresentationManager() != null) {
            ctx.getPresentationManager().error(e);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
