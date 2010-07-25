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

/**EntityOwner is the representation of the Entity Owner in weak entities. Programmer must define
 * the owner id, the owner (normally a collection) property that points to and, if exists, the property
 * of the local entity that points to the owner.
 * <pre>
 * {@code
 *  <owner>
 *     <entityId>owner_entity_id</entityId>
 *     <entityProperty>owner_property</entityProperty>
 *     <localProperty>local_pointer_to_owner</localProperty>
 *     <entityCollectionClass></entityCollectionClass>
 *  </owner>
 * }
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * @see Entity#owner
 * */
public class EntityOwner extends PMCoreObject{
    /**The id of the owner entity*/
    private String entityId;
    /**The owner's entity property that contains the weak entity*/
    private String entityProperty;
    /**The property of the local entity that points to the owner (optional)*/
    private String localProperty;
    /**The collection class*/
    private String entityCollectionClass;
    /**Optional list position on ordered lists */
    private String localPosition;
    
    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }
    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    /**
     * @return the entityProperty
     */
    public String getEntityProperty() {
        return entityProperty;
    }
    /**
     * @param entityProperty the entityProperty to set
     */
    public void setEntityProperty(String entityProperty) {
        this.entityProperty = entityProperty;
    }
    /**
     * @return the localProperty
     */
    public String getLocalProperty() {
        return localProperty;
    }
    /**
     * @param localProperty the localProperty to set
     */
    public void setLocalProperty(String localProperty) {
        this.localProperty = localProperty;
    }
    /**
     * @return the entityCollectionClass
     */
    public String getEntityCollectionClass() {
        return entityCollectionClass;
    }
    /**
     * @param entityCollectionClass the entityCollectionClass to set
     */
    public void setEntityCollectionClass(String entityCollectionClass) {
        this.entityCollectionClass = entityCollectionClass;
    }

    /**
     * Getter for localPosition
     * @return The localPosition
     */
    public String getLocalPosition() {
        return localPosition;
    }

    /**
     *
     * @param localPosition
     */
    public void setLocalPosition(String localPosition) {
        this.localPosition = localPosition;
    }

    
}
