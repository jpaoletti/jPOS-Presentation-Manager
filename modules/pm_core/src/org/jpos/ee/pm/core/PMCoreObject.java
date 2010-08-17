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

import org.jpos.util.Log;

/**This is the superclass of all the core objects of Presentation Manager and it provides some
 * helpers.
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public abstract class PMCoreObject {
    public static final String ENTITY = "entity";
    public static final String PM_OPERATION = "PM_OPERATION";
    public static final String PM_ENTITY_CONTAINER = "PM_ENTITY_CONTAINER";
    public static final String PM_OWNER = "PM_OWNER";
    public static final String PM_FIELD = "PM_FIELD";
    public static final String PM_ENTITY_INSTANCE = "PM_ENTITY_INSTANCE";
    public static final String PM_FIELD_VALUE = "PM_FIELD_VALUE";
    public static final String PM_CONTEXT = "PM_CONTEXT";
    public static final String PM_ENTITY_INSTANCE_WRAPPER = "PM_ENTITY_INSTANCE_WRAPPER";
    public static final String PM_LIST_ORDER = "PM_LIST_ORDER";
    public static final boolean PM_LIST_ASC = true;
    public static final String PM_EXTRA_DATA = "PM_EXTRA_DATA";
    public static final String PM_ENTITY = "PM_ENTITY";

    public static final String SCOPE_GRAL = "general";
    public static final String SCOPE_ITEM = "item";
    public static final String SCOPE_SELECTED = "selected";


    private Boolean debug;

    /**
     * Display a debug information on PM log if debug flag is active
     *
     * @param s String information
     */
    public void debug(String s) {
        if (getDebug()) {
            PresentationManager.pm.debug(this, s);
        }
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
        if (debug == null) {
            return false;
        }
        return debug;
    }

    /**
     *
     * @return
     */
    public Log getLog() {
        return PresentationManager.pm.getLog();
    }

    /**
     * Return the presentation manager singleton
     *
     * @return The Presentation Manager
     */
    protected PresentationManager getPresentationManager() {
        return PresentationManager.pm;
    }
}
