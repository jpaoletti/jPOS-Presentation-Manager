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

import org.jpos.ee.pm.validator.Validator;

/**An Operation is any action that can be applied to an Entity or to an Entity Instance. Most common
 * operations are list, add, delete, edit and show but programmer can define any new. To give it a new
 * style or icon there must be a css class defined on your template/buttons.css with the class name equals
 * to operation id. The title of the operation is determined by an entry in the ApplicationResource file 
 * with the key "operation._op_id_" <br>
 * 
 * <pre>
 * {@code
 * <operation id="some_id" enabled="true" scope="general | item | selected" display="all | add list edit">
 *    <showTitle>true</showTitle>
 *    <confirm>true</confirm>
 *    <context class="some.operation.Context" />
 *    <validator class="some.validator.Validator1" />
 *    <validator class="some.validator.Validator2" />
 * </operation>
 * }
 * </pre>
 *  
 * @author yero jeronimo.paoletti@gmail.com
 * */

public class Operation extends PMCoreObject {
    /**The operation Id. Must be unique and only one word */
    private String id;
    
    /**Determine if the operation is enabled or not.*/
    private Boolean enabled;
    
    /**Scope of the operation. Possibles values are:
     * <dl>
     * <dd> general </dd><dt>A general scope operation affects all the instances of the entity or none of them. </dt>
     * <dd> item </dd><dt>An item scope operation affects only one instance.</dt>
     * <dd> selected </dd><dt>A selected scope operation affects only selected instances.</dt>
     * </dl>
     * */
    private String scope;
    
    /**A String with other operations id separated by blanks where this operation will be shown*/
    private String display;
    
    /**If defined, its a direct link to a fixed URL*/
    private String url;
    
    /** Indicates if the entity's title is shown */
    private Boolean showTitle;
    
    /** Indicate if a confirmation is needed before proceed.*/
    private Boolean confirm;
    
    /**@see OperationContext*/
    private OperationContext context;
    
    /**A list of validators for the operation.*/
    private ArrayList<Validator> validators;
    
    /**A properties object to get some extra configurations*/
    private Properties properties;
    
    /**Determine if this operation is visible in another. 
     * @param other The id of the other operation
     * @return true if this operation is visible in the other*/
    public boolean isDisplayed(String other){
        return (getDisplay()==null || getDisplay().compareTo("all")==0 || getDisplay().indexOf(other)!=-1);
    }

    /**
     * Redefines toString from object
     * @return
     */
    @Override
    public String toString() {
        return getId();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
    	if(enabled==null) return true;
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the scope
     */
    public String getScope() {
    	if(scope==null || scope.trim().compareTo("")==0) return "item";
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the display
     */
    public String getDisplay() {
    	if(display==null || display.trim().compareTo("")==0) return "all";
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the context
     */
    public OperationContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(OperationContext context) {
        this.context = context;
    }

    /**
     * @return the validators
     */
    public ArrayList<Validator> getValidators() {
        return validators;
    }

    /**
     * @param validators the validators to set
     */
    public void setValidators(ArrayList<Validator> validators) {
        this.validators = validators;
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

    /**
     *
     * @param showTitle
     */
    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    /**
     * 
     * @return
     */
    public Boolean getShowTitle() {
        if(showTitle==null)return true;
        return showTitle;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    /**
     * @return the confirm
     */
    public Boolean getConfirm() {
        if(confirm == null) return false;
        return confirm;
    }
}