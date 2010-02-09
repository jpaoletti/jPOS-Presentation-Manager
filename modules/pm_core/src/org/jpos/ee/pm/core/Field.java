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

import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.converter.Converters;
import org.jpos.ee.pm.validator.Validator;

/**A Field represents an attribute of the represented entity. 
 * 
 * <h2>Simple entity configuration file</h2>
 * <pre>
 * {@code
 * <field>
 *     <id>some_id</id>
 *     ....
 * </field>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class Field extends PMCoreObject{
	/**The id of the field, there must be a getter and a setter for this name on the represented entity.*/
	private String id;
	
	/**A descriptive name*/
	//private String name;
	
	/**A short name
	 * @deprecated*/
	private String shortName;
	
	/**The width of the field value*/
	private String width;
	private String display;
	private int size;
	private int maxLength;
	/**@deprecated*/
	private boolean sortable;
	/**@deprecated*/
	private boolean searchable;
    private ArrayList<Validator> validators;
	private Converters converters;
	/**@deprecated*/
    private String searchCriteria; 
    private String defaultValue;   
    private String align; //left right center          
    private boolean multiEditable; 
    
    
    public Field () {
        super();
        align="left";
        multiEditable=false;
        defaultValue="";
    }
    
    private String visualizeW(String s){
    	return getService().visualizationWrapper(s);
    }
    
    public String visualize(Entity entity, Operation operation, Object item, String extra) throws ConverterException{
    	//debug("Converting ["+operation.getId()+"]"+entity.getId()+"."+getId());
    	try {
        	if(getConverters()!= null){
        		Converter c = getConverters().getConverterForOperation(operation.getId());
        		if(c!= null) 
        			return visualizeW((c.visualize(entity,this,operation,new EntityInstanceWrapper(item),extra)));
        	}
        	return visualizeW(EntitySupport.getAsString(item, this.getId()));
		} catch (Exception e) {
			PMLogger.error(e);
			throw new ConverterException("Unable to convert "+entity.getId()+"."+getId());
		}
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
    /*public void setName (String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setShortName (String shortName) {
        this.shortName = shortName;
    }
    public String getShortName() {
        return shortName != null ? shortName : name;
    }*/

/*    public void setReadPerm (Permission readPerm) {
        this.readPerm = readPerm;
    }
    public Permission getReadPerm () {
        return readPerm;
    }
    public void setWritePerm (Permission writePerm) {
        this.writePerm = writePerm;
    }
    public Permission getWritePerm () {
        return writePerm;
    }*/
    public void setMaxLength (int maxLength) {
        this.maxLength = maxLength;
    }
    public int getMaxLength() {
        return maxLength;
    }
    public void setSize (int size) {
        this.size = size;
    }
    public int getSize () {
        return size;
    }

    public void setDisplay (String display) {
        this.display = display;
    }
    public String getDisplay() {
        return display;
    }
    public void setSortable (boolean sortable) {
        this.sortable = sortable;
    }
    public boolean isSortable () {
        return sortable;
    }
    public void setSearchable (boolean searchable) {
        this.searchable = searchable;
    }
    public boolean isSearchable () {
        return searchable || "id".equals (getId());
    }
    public boolean shouldDisplay (String action) {
        if (action == null || display == null 
            || "list show edit add".indexOf(action) < 0) 
        {
            return false;
        }
        return "all".equalsIgnoreCase(display) || display.indexOf (action) >= 0;
    }
    // Helpers
    public boolean canUpdate () {
        return isDisplayInEdit();
    }
    public boolean isDisplayInList () {
        return shouldDisplay("list");// && hasPermission (getReadPerm());
    }
    public boolean isDisplayInShow() {
        return shouldDisplay("show");// && hasPermission (getReadPerm());
    }
    public boolean isDisplayInEdit() {
        return shouldDisplay("edit");// && hasPermissions (getReadPerm(), getWritePerm());
    }
    public boolean isDisplayInAdd() {
        return shouldDisplay("add");// && hasPermissions (getReadPerm(), getWritePerm());
    }
    /*public boolean hasPermission (Permission permName) {
        if (permName != null) {
            SECUser u = PresentationManagerAction.getContextUser();
            if (u == null)
                return false;
            return u.hasPermission(permName);
        }
        return true;
    }
    public boolean hasPermissions (Permission perm0, Permission perm1) {
        SECUser u = PresentationManagerAction.getContextUser();
        if ((u == null) ||
            (perm0 != null && !u.hasPermission(perm0)) ||
            (perm1 != null && !u.hasPermission(perm1)))
            return false;
        return true;
    }*/

    public Object fromString (String s) {
        return s;
    }
   
    public String toString (Object obj) {
        return obj.toString();
    }
    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
    public String getSearchCriteria() {
        return searchCriteria;
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
    public void setMultiEditable(boolean multiEditable) {
        this.multiEditable = multiEditable;
    }
    public boolean isMultiEditable() {
        return multiEditable;
    }


	public void setWidth(String width) {
		this.width = width;
	}


	public String getWidth() {
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


}