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

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jpos.ee.Constants;
import org.jpos.ee.DB;
import org.jpos.ee.DBSupport;
import org.jpos.ee.pm.security.SECUser;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsService;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

/**A super class for all actions with some helpers and generic stuff*/
public abstract class ActionSupport extends Action implements Constants{

    protected abstract ActionForward doExecute(RequestContainer rc)throws Exception;
    
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){ 	return true;}
    
	protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		rc.setDbs(new DBSupport(rc.getDB()));
        if (!rc.isUserOnLine() && checkUser()) {
            return rc.getMapping().findForward(STRUTS_LOGIN);
        }
        return null;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		debug("Starting");
		RequestContainer rc = new RequestContainer();
		rc.setMapping(mapping);
		rc.setForm(form);
		rc.setRequest(request);
		rc.setResponse(response);
		rc.setErrors(new ActionErrors());
		rc.setErrorlist(new HashMap<String, String>());
		
		debug("RC Built");
		ActionForward r = preExecute(rc);
		debug("Pre Execute Done: "+r);
		r = excecute(rc,r);
		debug("Execute Done: "+r);
		if(rc.getErrors().size()>0){
			debug(rc.getErrors().toString());
			saveErrors(request, rc.getErrors());
		}
		debug("Finish");
    	return r;
	}
	
	protected ActionForward excecute(RequestContainer rc, ActionForward r) throws Exception {
		return null;
	}

    protected PMStrutsService getPMService()throws NameRegistrar.NotFoundException{
    	return (PMStrutsService) PMEntitySupport.staticPmservice();
    }
    
	//TODO Make a better auditory system    
	protected void logRevision (DB db, String ref, String info, SECUser author) {        
		/*RevisionEntry re = new RevisionEntry();        
		re.setDate (new Date());        
		re.setInfo (info);
		re.setAuthor (author);        
		//re.setRef (ref);        
		db.session().save (re);*/
	}

	protected void debug(String s){
		System.out.println("["+this.getClass().getName()+"] DEBUG: "+s);
	}
}