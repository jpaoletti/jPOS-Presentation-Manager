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
package org.jpos.ee.pm.converter;

import java.util.Properties;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**A converter is an object associated to a field that determine the way the field value
 * will be visualized and build from a visual representation to a value for a given 
 * operation.
 * <pre>
 * {@code
 * <field>
 *     ...
 *     <converters>
 *         <converter class="some.converter.Class">
 *            <operationId>add</operationId>
 *            <properties>
 *                <property name="propname" value="propvalue" />
 *            </properties>
 *         </converter>
 *     </converters>
 * </field>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 **/
public abstract class Converter {
	/**The operation id where the converter will be applied*/
	private String operationId;
	/**A properties object to get some extra configurations*/
	private Properties properties;
    
	/**This method transforms the given value into a String to visualize it
	 * @param entity The entity
	 * @param field The field
	 * @param operation The operation in which the converter will be applied
	 * @param einstance The entity instance just in case the value is affected for some other field in some specific situation 
	 * @param extra Some extra information 
	 * @return The string representation of the object 
	 * @throws ConverterException*/
	public abstract String visualize(Entity entity, Field field, Operation operation, EntityInstanceWrapper einstance, String extra) throws ConverterException;
	
	/**This method takes a specific format of the object from the visualization (usually a string) and
	 * transforms it in the required object.
	 * @param entity The entity
	 * @param field The field
	 * @param operation The operation in which the converter will be applied
	 * @param einstance The entity instance just in case the value is affected for some other field in some specific situation
	 * @param value The value from the visualization engine
	 * @return The value to be set in the entity instance.
	 * @throws ConverterException 
	 * */
	public abstract Object build(Entity entity, Field field, Operation operation, EntityInstanceWrapper einstance, Object value) throws ConverterException;
	
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

    /**Getter for the value
     * @param einstance The entity instance
     * @param field The field
     * @return The field value on the entity instance*/
	protected Object getValue(Object einstance, Field field) {
		return getNestedProperty(einstance, field.getId());
	}
    
    /**Getter for a nested property in the given object.
     * @param obj The object
     * @param propertyName The name of the property to get
     * @return The property value */
    public Object getNestedProperty (Object obj, String propertyName) {
        try {
            if (obj != null && propertyName != null)
                return PropertyUtils.getNestedProperty (obj, propertyName);
        } catch (NullPointerException e) {
            // OK to happen
        } catch (NestedNullException e) {
            // Hmm... that's fine too
        } catch (Exception e) {
            // Now I don't like it.
            return e.getClass().getName() + ":" + e.getMessage();
        }
        return null;
    }
	
	/**
	 * @param operationId the operationId to set
	 */
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	/**
	 * @return the operationId
	 */
	public String getOperationId() {
		return operationId;
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