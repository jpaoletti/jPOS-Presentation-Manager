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
package org.jpos.ee.pm.core;

import org.jpos.ee.Constants;
import org.jpos.util.Log;


/**This is the superclass of all the core objects of Presentation Manager and it provides some
 * helpers.
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public abstract class PMCoreObject implements Constants{
    private PMService service;
    private Boolean debug;

    /**
     * @param service the service to set
     */
    public void setService(PMService service) {
        this.service = service;
    }

    /**
     * @return the service
     */
    public PMService getService() {
        return service;
    }
    
    public void debug(String s){
        if(getDebug()) PMLogger.debug(this,s);
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the debug
     */
    public Boolean getDebug() {
        if(debug==null) return false;
        return debug;
    }
    
    public Log getLog(){
        return PMLogger.getLog();
    }
}