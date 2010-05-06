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

import org.apache.struts.action.ActionForm;

public class ChangePasswordForm extends ActionForm {
    private static final long serialVersionUID = 4309158053383220079L;
    private String actual;
    private String newpass;
    private String newrep;
    private String finish;
    
    /**
     * @param actual the actual to set
     */
    public void setActual(String actual) {
        this.actual = actual;
    }
    /**
     * @return the actual
     */
    public String getActual() {
        return actual;
    }
    /**
     * @param newpass the newpass to set
     */
    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
    /**
     * @return the newpass
     */
    public String getNewpass() {
        return newpass;
    }
    /**
     * @param newrep the newrep to set
     */
    public void setNewrep(String newrep) {
        this.newrep = newrep;
    }
    /**
     * @return the newrep
     */
    public String getNewrep() {
        return newrep;
    }
    public void setFinish(String finish) {
        this.finish = finish;
    }
    public String getFinish() {
        return finish;
    }


}

