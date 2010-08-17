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
package org.jpos.ee.pm.validator;

import java.util.Properties;


/**Helper class for {@link Validator}. Includes a DB object and a properties attribute to define
 * attributes in the configuration file.
 * <pre>
 * {@code
 * <!DOCTYPE entity SYSTEM "cfg/entity.dtd">
 * <operation>
 *     <validator class="org.jpos.SomeValidator">
 *         <properties>
 *             <property name="somename" value="somevalue" />
 *         </properties>
 *     </validator>
 * </operation>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public abstract class ValidatorSupport extends org.jpos.util.Validator implements Validator{
    /**The properties*/
    private Properties properties;
     
    /**Helper for a property
     * @param name Property name
     * @param def Default value
     * @return Property value*/
    public String get (String name, String def) {
        if (properties != null) {
            Object obj = properties.get (name);
            if (obj instanceof String)
                return obj.toString();
        }
        return def;
    }
    
    /**Helper for an int property
     * @param name Property name
     * @return Property value as int*/
    public Integer getInt (String name) {
        try {
            return Integer.parseInt(get(name, "").trim());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}