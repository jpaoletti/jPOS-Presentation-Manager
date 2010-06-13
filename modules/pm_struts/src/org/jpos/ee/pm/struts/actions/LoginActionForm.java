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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jpos.util.Validator;

public class LoginActionForm extends ActionForm {
    private static final long serialVersionUID = 959334757634953090L;
    private String username;
    private String password;
    private boolean remember;
    
    public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(username == null || username.compareTo("") == 0)
            errors.add("username",new ActionMessage("login.username.null"));
        if(password == null || password.compareTo("") == 0)
            errors.add("password",new ActionMessage("login.password.null"));
        
        if (username!= null && !Validator.isName (username)) 
            errors.add("username",new ActionMessage("login.username.invalid"));

        return errors;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRemember(boolean remember) {
        this.remember = remember;
    }
    public boolean getRemember() {
        return remember;
    }
}
