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
public class Field extends PMCoreObject{
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
    
    public Field () {
        super();
        align="left";
        defaultValue="";
    }

    public String visualize(PMContext ctx) throws PMException{
        debug("Converting ["+ctx.getOperation().getId()+"]"+ctx.getEntity().getId()+"."+getId());
        try {
            Converter c = null;
            if(getConverters()!= null){
                c = getConverters().getConverterForOperation(ctx.getOperation().getId());
            }
            if(c == null){
                c = new GenericConverter();
                c.setService(getService());
                Properties properties = new Properties();
                properties.put("filename", "cfg/converters/show.tostring.converter");
                c.setProperties(properties);
            }
            ctx.put(PM_ENTITY_INSTANCE_WRAPPER, new EntityInstanceWrapper(ctx.get(PM_ENTITY_INSTANCE)));
            ctx.put(PM_FIELD, this);
            return getService().visualizationWrapper(c.visualize(ctx));
        } catch (Exception e) {
            PMLogger.error(e);
            throw new ConverterException("Unable to convert "+ctx.getEntity().getId()+"."+getProperty());
        }
    }
    
    /**Set also converters service
     * @see org.jpos.ee.pm.core.PMCoreObject#setService(org.jpos.ee.pm.core.PMService)
     */
    public void setService(PMService service) {
        super.setService(service);
        getConverters().setService(service);
    }

    public int compareTo(Field o) {
        return getId().compareTo(o.getId());
    }
    
    public ArrayList<Validator> getValidators() {
        return validators;
    }

    public void setValidators(ArrayList<Validator> validators) {
        this.validators = validators;
    }

    public void setId (String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setDisplay (String display) {
        this.display = display;
    }

    /** @return The list (separated by blanks) of operations id where this field
     * will be displayed */
    public String getDisplay() {
    	if(display==null || display.trim().compareTo("")==0) return "all";
        return display;
    }

    /**Indicates if the field is shown in the given operation id
     *
     * @param operationId  The Operation id
     * @return true if field is displayed on the operation
     */
    public boolean shouldDisplay (String operationId) {
        if (operationId == null || getDisplay() == null) return false;
        return "all".equalsIgnoreCase(getDisplay()) || getDisplay().indexOf (operationId) >= 0;
    }
    
    @Deprecated
    public Object fromString (String s) {
        return s;
    }

    @Deprecated
    public String toString (Object obj) {
        return obj.toString();
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
    public void setAlign(String align) {
        this.align = align;
    }
    public String getAlign() {
        return align;
    }

    public void setWidth(String width) {
        this.width = width;
    }


    public String getWidth() {
    	if(width==null) return "";
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
        if(converters==null) converters = new Converters();
        return converters;
    }

    public String toString() {
    	return id;
    }

    /** @return the property of the entity instance object that can be accesed by
     * getter and setter. Default value is the field id
     */
    public String getProperty() {
        String r = property;
        if(r == null || r.trim().equals("")) r = id;
        return r;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}