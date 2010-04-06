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
package org.jpos.ee.pm.core;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

/**Helper class for introspection and some extra stuff
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class EntitySupport {
    
    /***/
    public static synchronized PMService staticPmservice(){
        try {
            return ((PMService)NameRegistrar.get(PMService.getCustomName()));
        } catch (NotFoundException e) {
            return null;
        }        
    }

    public synchronized PMService getPmservice(){
        return staticPmservice();
    }

    /**Getter for an object property value as String
     * @param obj The object
     * @param propertyName The property
     * @return The value of the property of the object as string
     * */
    public static String getAsString(Object obj, String propertyName){
        Object o = get(obj, propertyName);
        if(o != null) return o.toString(); else return "";
    }
    
    /**Getter for an object property value
     * @param obj The object
     * @param propertyName The property
     * @return The value of the property of the object
     * */
    public static Object get (Object obj, String propertyName) {
        try {
            if (obj != null && propertyName != null)
                return PropertyUtils.getNestedProperty (obj, propertyName);
        } catch (NullPointerException e) {
            // OK to happen
        } catch (NestedNullException e) {
            // Hmm... that's fine too
        } catch (Exception e) {
            // Now I don't like it.
            PMLogger.error(e);
            return "-undefined-";
        }
        return null;
    }
    
    
    /**Setter for an object property value
     * @param obj The object
     * @param propertyName The property
     * @param value The value to set
     * */
    public static void set (Object obj, String name, Object value) {
        try {
            PropertyUtils.setNestedProperty (obj, name, value);
        } catch (Exception e) {
            PMLogger.error(e);
        }
    }

    public static Object newObjectOf(String clazz) {
        try {
            return Class.forName(clazz).newInstance();
        } catch (Exception e) {
            PMLogger.error(e);
            return null;
        }
    }
}