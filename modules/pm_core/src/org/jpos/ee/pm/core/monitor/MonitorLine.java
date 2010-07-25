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
package org.jpos.ee.pm.core.monitor;

/** 
 * A monitor line. Its composed by an id to identificate the line and a content.
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * http://github.com/jpaoletti/jPOS-Presentation-Manager
 * 
 * */
public class MonitorLine {

    private Object id;
    private Object value;

    /**
     * setter for the id
     * @param id
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * getter for the id
     * @return The id
     */
    public Object getId() {
        return id;
    }

    /**
     * setter for the value
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * getter for the value
     * @return The value
     */
    public Object getValue() {
        return value;
    }
}
