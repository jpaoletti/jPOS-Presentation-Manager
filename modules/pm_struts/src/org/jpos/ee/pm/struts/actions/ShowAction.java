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

/**Action for show operation. */
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class ShowAction extends RowActionSupport {

    protected boolean isAudited() {    return false; }
    
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        if(ctx.getSelected() == null){
            throw new PMException("pm.instance.not.found");
        }
        return true;
    }
    
    protected void doExecute(PMStrutsContext ctx) throws PMException {}
}