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

import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMSession;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.menu.MenuSupport;
import org.jpos.ee.pm.security.core.InvalidPasswordException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.security.core.UserNotFoundException;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class LoginAction extends EntityActionSupport {

    /** Opens an hibernate transaction before doExecute*/
    @Override
    protected boolean openTransaction() {
        return false;
    }

    /**Makes the operation generate an audithory entry*/
    @Override
    protected boolean isAudited() {
        return false;
    }

    /**Forces execute to check if any user is logged in*/
    @Override
    protected boolean checkUser() {
        return false;
    }

    /**Forces execute to check if there is an entity defined in parameters*/
    @Override
    protected boolean checkEntity() {
        return false;
    }

    @Override
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        if (ctx.getPresentationManager().isLoginRequired()) {
            return super.prepare(ctx);
        } else {
            return true;
        }
    }

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        PMSession session = ctx.getPresentationManager().registerSession(ctx.getSession().getId());
        ctx.getSession().setAttribute(PMEntitySupport.PMSESSION, session);
        if (ctx.getPresentationManager().isLoginRequired()) {
            try {
                ctx.getSession().setAttribute(USER, null);
                ctx.getSession().setAttribute(MENU, null);
                PMSecurityUser u = authenticate(ctx);
                session.setUser(u);
                session.setMenu(loadMenu(ctx, u));
                if (u.isChangePassword()) {
                    throw new PMForwardException("changepassword");
                }

            } catch (UserNotFoundException e) {
                throw new PMException("pm_security.user.not.found");
            } catch (InvalidPasswordException e) {
                throw new PMException("pm_security.password.invalid");
            } catch (Exception e) {
                ctx.getPresentationManager().error(e);
                throw new PMException("pm_core.unespected.error");
            }
        } else {
            PMSecurityUser u = new PMSecurityUser();
            u.setName(" ");
            session.setUser(u);
            session.setMenu(loadMenu(ctx, u));
        }
    }

    private Menu loadMenu(PMStrutsContext ctx, PMSecurityUser u) throws PMException {
        Menu menu = MenuSupport.getMenu(u.getPermissionList());
        ctx.getSession().setAttribute(USER, u);
        ctx.getSession().setAttribute(MENU, menu);
        return menu;
    }

    /**
     * @param ctx The context with all the parameters
     * @return The user
     * @throws BLException
     */
    private PMSecurityUser authenticate(PMStrutsContext ctx) throws PMSecurityException {
        PMSecurityUser u = null;
        LoginActionForm f = (LoginActionForm) ctx.getForm();
        String seed = ctx.getSession().getId();
        u = getConnector(ctx).authenticate(f.getUsername(), decrypt(f.getPassword(), seed));
        return u;
    }

    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

    private String decrypt(String password, String seed) {
        return password;
    }
}
