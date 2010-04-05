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

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jpos.ee.Constants;
import org.jpos.ee.DB;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.struts.PMEntitySupport;

public class GeneralFilter implements Filter,Constants {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		PMEntitySupport o = (PMEntitySupport) req.getSession().getAttribute(ENTITY_SUPPORT);
		if(o == null){
			PMEntitySupport es = PMEntitySupport.getInstance();
			es.setContext_path(req.getContextPath());
			req.getSession().setAttribute(ENTITY_SUPPORT, es);
		}
		PMContext ctx = new PMContext();
		ctx.setRequest((HttpServletRequest) request);
		ctx.setResponse((HttpServletResponse) response);
		ctx.setErrors(new ArrayList<PMMessage>());
		ctx.getRequest().setAttribute(PM_CONTEXT, ctx);
		
		try {
			connectToDB(ctx);
			
			chain.doFilter(request, response);
		} catch (ServletException e) {
			PMLogger.error(e);
			throw e;
		}finally{
			if(ctx.get(DB)!=null){
				((DB)ctx.get(DB)).close();
			}
		}
	}

	private void connectToDB(PMContext ctx) throws ServletException {
		if(PMEntitySupport.staticPmservice()!=null && !PMEntitySupport.staticPmservice().ignoreDb()){
			try {
				DB db = new DB(PMLogger.getLog());
				db.open();
				ctx.getSession().setAttribute(DB, db);
				ctx.put(DB, db);
			} catch (Exception e) {
				PMLogger.error("No se pudo acceder a la Base de Datos");
				throw new ServletException(e);
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
