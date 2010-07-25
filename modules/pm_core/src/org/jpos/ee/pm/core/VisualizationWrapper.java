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

import java.util.Map;

/**This class provides a way to add read only fields to any operation of an entity. A programmer can subclass 
 * this class to add visualization functionality such as balances, counters or any complex or simple function
 * that requires more than a getter.<br/>
 * 
 * Not yet implemented
 *    
 * @author jpaoletti jeronimo.paoletti@gmail.com
 *  
 * */
public abstract class VisualizationWrapper {
    /** A map with the calculated values of the field*/    
    private Map<String,Object> values;

    /**
     * This abstract method must be defined for each implementation and must
     * fill the values map.
     * @param ctx The context
     * @param object The entity instance to get any data required for calculation*/
    public abstract void load(PMContext ctx,  Object object);
    
    /**This function is automatically invoked to get the values configured in the entity 
     * configuration file
     * 
     * @param name The name of the requested value 
     * @return The value for the given field name
     * */
    public Object get(String name){
        return values.get(name);
    }
}
