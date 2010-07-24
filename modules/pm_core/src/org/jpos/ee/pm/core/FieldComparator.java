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

import java.util.Comparator;

/**This class is used to sort fields within the order attribute of Entity
 * @author yero
 * @see Entity#order
 * 
 * */
public class FieldComparator implements Comparator<Field> {
    /**A space separated string with the fields id */
    private String order;
    
    /**Constructor with the specified order
     *@param order The order */
    public FieldComparator(String order){
        this.order = order;
    }

    /**Compare method.
     * @param o1 First field to compare
     * @param o2 Second field to compare
     * @return The lesser looking at order property
     * */
    public int compare(Field o1, Field o2) {
        int i = order.indexOf(o1.getId());
        int j = order.indexOf(o2.getId());
        if(i==j) return 0;
        if(i==-1 && j >= 0) return 1;
        if(j==-1 && i >= 0) return -1;
        return i - j;
    }

    /**Setter for order
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**Getter for order
     * @return the order
     */
    public String getOrder() {
        return order;
    }

}
