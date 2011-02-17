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

/***/
public class Highlight {
    /***/
    private String field;
    /***/
    private String value;
    /***/
    private String color;
    /**instance: all the item. property: only the value */
    private String scope;

    public boolean isInstance(){
        return "instance".equals(getScope());
    }
        
    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
    /**
     * @return the scope. 'instance' for the whole item and 'property' for just
     * the instance property. instance by default
     */
    public String getScope() {
        if(scope==null) return "instance";
        return scope;
    }
    
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }
    /**
     * @return the field
     */
    public String getField() {
        return field;
    }    
}