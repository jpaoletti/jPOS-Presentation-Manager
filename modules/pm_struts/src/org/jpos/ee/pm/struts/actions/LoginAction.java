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

import org.apache.struts.action.ActionMessages;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.menu.MenuSupport;
import org.jpos.ee.pm.security.core.InvalidPasswordException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.security.core.UserNotFoundException;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class LoginAction extends EntityActionSupport {

    /** Opens an hibernate transaction before doExecute*/
    protected boolean openTransaction() { return false;    }
    /**Makes the operation generate an audithory entry*/
    protected boolean isAudited() {    return false; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){     return false;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return false; }

    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        if(getPMService().isLoginRequired()){
            return super.prepare(ctx);
        }else{
            return true;
        }
    }
    protected void doExecute(PMStrutsContext ctx) throws PMException {
         if(getPMService().isLoginRequired()){
            ctx.getSession().setAttribute(USER, null);
            ctx.getSession().setAttribute(MENU, null);
            
            try {
                 PMSecurityUser u = authenticate(ctx);
    
                 loadMenu(ctx, u);
                 
                 if (u.isChangePassword())
                     throw new PMForwardException("changepassword");

             } catch (UserNotFoundException e) {
                    ctx.getErrors().add(new PMMessage(ActionMessages.GLOBAL_MESSAGE, "pm_security.user.not.found"));
                    throw new PMException();
             } catch (InvalidPasswordException e) {
                    ctx.getErrors().add(new PMMessage(ActionMessages.GLOBAL_MESSAGE, "pm_security.password.invalid"));
                    throw new PMException();
             } catch (Exception e) {
                PMLogger.error(e);
                ctx.getErrors().add(new PMMessage(ActionMessages.GLOBAL_MESSAGE, "pm_core.unespected.error"));
                throw new PMException();
             }
         }else{
             PMSecurityUser u = new PMSecurityUser();
             u.setName(" ");
             loadMenu(ctx, u);
         }
    }
    private void loadMenu(PMStrutsContext ctx, PMSecurityUser u) throws PMException{
        Menu menu = MenuSupport.getMenu(u,getPMService());
         ctx.getSession().setAttribute(USER, u);
         ctx.getSession().setAttribute(MENU, menu);
    }
    /**
     * @param accessCount
     * @param seed
     * @param cfgMgr
     * @param mgr
     * @return The user
     * @throws BLException
     */
    private PMSecurityUser authenticate(PMStrutsContext ctx) throws PMSecurityException {
        PMSecurityUser u = null;
        LoginActionForm f = (LoginActionForm) ctx.getForm();
        String seed = ctx.getSession().getId();
        u = getConnector(ctx).authenticate(f.getUsername(), decrypt(f.getPassword(),seed));
        return u;
    }
    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

    private String decrypt(String password, String seed) {
        return password;
    }
}
