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
package org.jpos.ee.pm.security.ui.actions;

import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.security.core.InvalidPasswordException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.ee.pm.struts.actions.RowActionSupport;

public class ChangePasswordAction extends RowActionSupport {

    /** Opens an hibernate transaction before doExecute*/
    protected boolean openTransaction() { return true;    }
    /**Makes the operation generate an audithory entry*/
    protected boolean isAudited() {    return false; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){     return true;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }
    
    public boolean testSelectedExist(){ return false; }
    
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        ctx.getEntityContainer().setSelected( new EntityInstanceWrapper(ctx.getUser()) );
        if(ctx.getRequest().getParameter(FINISH)==null){
            throw new PMForwardException(CONTINUE);
        }
        return true;
    }
    
    protected void doExecute(PMStrutsContext ctx) throws PMException {
        ChangePasswordForm f = (ChangePasswordForm) ctx.getForm();
        PMSecurityUser u = (PMSecurityUser) ctx.getSelected().getInstance();
        try {
            if(f.getFinish()!=null){
                validate(u, ctx, f);
            }
            if(!ctx.getErrors().isEmpty()) 
                throw new PMException();
            getConnector(ctx).changePassword(u.getUsername(), f.getActual(), f.getNewpass() );
        } catch (InvalidPasswordException e){
            throw new PMException("pm_security.password.invalid");
        } catch (PMSecurityException e) {
            e.printStackTrace();
            throw e;
        }
        
        if(!ctx.getErrors().isEmpty()) 
            throw new PMException();
        
    }
    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }
    private void validate(PMSecurityUser user, PMContext ctx, ChangePasswordForm form) {
        if(form.getActual()==null || form.getActual().trim().compareTo("")==0) 
            ctx.getErrors().add(new PMMessage("actual","chpass.actual.not.null"));
        if(form.getNewpass()==null || form.getNewpass().trim().compareTo("")==0) 
            ctx.getErrors().add(new PMMessage("newpass","chpass.newpass.not.null"));
        if(form.getNewrep()==null|| form.getNewrep().trim().compareTo("")==0) 
            ctx.getErrors().add(new PMMessage("newrep","chpass.newrep.not.null"));
        if(ctx.getErrors().isEmpty()){
            if(form.getNewpass().compareTo(form.getNewrep())!=0) 
                ctx.getErrors().add(new PMMessage("newrep","chpass.newrep.diferent"));
            
            if(form.getNewpass().compareTo(form.getActual())==0) 
                ctx.getErrors().add(new PMMessage("newrep","chpass.repeated.passw"));
        }
    }
}
