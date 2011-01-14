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

import java.util.ArrayList;
import java.util.Properties;

import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.converter.Converters;
import org.jpos.ee.pm.converter.GenericConverter;
import org.jpos.ee.pm.validator.Validator;

/**A Field represents a property of the represented entity.
 * 
 * <h2>Simple entity configuration file</h2>
 * <pre>
 * {@code
 * <field id="id" display="all | some_operations" align="right | left | center" width="xxxx" />
 *     ....
 * </field>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class Field extends PMCoreObject {

    /**The id of the field, must be unique in the entity*/
    private String id;
    /**The property to be accesed on the entity instance objects. There must be
     * a getter and a setter for this name on the represented entity. When null,
    default is the field id*/
    private String property;
    /**The width of the field value*/
    private String width;
    private String display;
    private ArrayList<Validator> validators;
    private Converters converters;
    private String defaultValue;
    private String align; //left right center          

    /**
     * Default constructor
     */
    public Field() {
        super();
        align = "left";
        defaultValue = "";
    }

    /**
     * Visualize the field using the given operation and entity
     * @param ctx the context
     * @param operation The operation
     * @param entity The entity
     * @return The string visualization
     * @throws PMException
     */
    public Object visualize(PMContext ctx, Operation operation, Entity entity) throws PMException {
        debug("Converting [" + operation.getId() + "]" + entity.getId() + "." + getId());
        try {
            Converter c = null;
            if (getConverters() != null) {
                c = getConverters().getConverterForOperation(operation.getId());
            }
            if (c == null) {
                c = getDefaultConverter();
            }
            final Object instance = ctx.get(PM_ENTITY_INSTANCE);
            ctx.put(PM_ENTITY_INSTANCE_WRAPPER, new EntityInstanceWrapper(instance));
            ctx.put(PM_FIELD, this);
            ctx.put(PM_FIELD_VALUE, getPresentationManager().get(instance,getProperty()));
            return c.visualize(ctx);
        } catch (Exception e) {
            getPresentationManager().error(e);
            throw new ConverterException("Unable to convert " + entity.getId() + "." + getProperty());
        }
    }

    /**
     * Return the default converter if none is defined
     *
     * @return The converter
     */
    public Converter getDefaultConverter() {
        if (getPresentationManager().getService().getDefaultConverter() == null) {
            Converter c = new GenericConverter();
            Properties properties = new Properties();
            properties.put("filename", "cfg/converters/show.tostring.converter");
            c.setProperties(properties);
            return c;
        }else{
            return getPresentationManager().getService().getDefaultConverter();
        }
    }

    /**
     * Visualize the field using the given operation and context entity
     * @param ctx the context
     * @param operation The operation
     * @return The String visualization
     * @throws PMException
     */
    public Object visualize(PMContext ctx, Operation operation) throws PMException {
        return visualize(ctx, operation, ctx.getEntity());
    }

    /**
     * Visualize the field using the context operation and entity
     * @param ctx The context
     * @return a String with the visualization
     * @throws PMException
     */
    public Object visualize(PMContext ctx) throws PMException {
        return visualize(ctx, ctx.getOperation());
    }

    /**
     *
     * @return
     */
    public ArrayList<Validator> getValidators() {
        return validators;
    }

    /**
     *
     * @param validators
     */
    public void setValidators(ArrayList<Validator> validators) {
        this.validators = validators;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param display
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * A (separated by blanks) list of operation ids where this field
     * will be displayed
     * 
     * @return The list
     */
    public String getDisplay() {
        if (display == null || display.trim().compareTo("") == 0) {
            return "all";
        }
        return display;
    }

    /**Indicates if the field is shown in the given operation id
     *
     * @param operationId  The Operation id
     * @return true if field is displayed on the operation
     */
    public boolean shouldDisplay(String operationId) {
        if (operationId == null || getDisplay() == null) {
            return false;
        }
        return "all".equalsIgnoreCase(getDisplay()) || getDisplay().indexOf(operationId) >= 0;
    }

    /**
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param align
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     *
     * @return
     */
    public String getAlign() {
        return align;
    }

    /**
     *
     * @param width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    public String getWidth() {
        if (width == null) {
            return "";
        }
        return width;
    }

    /**
     * @param converters the converters to set
     */
    public void setConverters(Converters converters) {
        this.converters = converters;
    }

    /**
     * @return the converters
     */
    public Converters getConverters() {
        if (converters == null) {
            converters = new Converters();
        }
        return converters;
    }

    /**
     * String representation of the field
     * @return
     */
    @Override
    public String toString() {
        return id;
    }

    /** @return the property of the entity instance object that can be accesed by
     * getter and setter. Default value is the field id
     */
    public String getProperty() {
        String r = property;
        if (r == null || r.trim().equals("")) {
            r = id;
        }
        return r;
    }

    /**
     * Setter for property
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }
}
