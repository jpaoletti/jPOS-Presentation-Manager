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

import java.util.List;
import java.util.Properties;

/** A monitor source is where the monitor takes the information
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * @see http://github.com/jpaoletti/jPOS-Presentation-Manager
 * 
 * */
public abstract class MonitorSource {
    private Properties properties;

    public abstract void init();
    public abstract List<MonitorLine> getLinesFrom(Object actual) throws Exception;
    public abstract MonitorLine getLastLine() throws Exception ;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }
    
    /**Getter for a specific property with a default value in case its not defined. 
     * Only works for string.
     * @param name Property name
     * @param def Default value
     * @return Property value only if its a string */
    public String getConfig (String name, String def) {
        if (properties != null) {
            Object obj = properties.get (name);
            if (obj instanceof String)
                return obj.toString();
        } 
        return def;
    }
    
    /**Getter for any property in the properties object
     * @param name The property name
     * @return The property value */
    public String getConfig (String name) {
        return getConfig (name, null);
    }
    
}
