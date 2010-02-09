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

/**EntityOwner is the representation of the Entity Owner in weak entities. Programmer must define
 * the owner id, the owner (normally a collection) property that points to and, if exists, the property
 * of the local entity that points to the owner.
 * <pre>
 * {@code
 *  <owner>
 *     <entity_id>owner_entity_id</entity_id>
 *     <entity_property>owner_property</entity_property>
 *     <local_property>local_pointer_to_owner</local_property>
 *  </owner>
 * }
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * @see Entity#owner
 * */
public class EntityOwner extends PMCoreObject{
	/**The id of the owner entity*/
	private String entity_id;
	/**The owner's entity property that contains the weak entity*/
	private String entity_property;
	/**The property of the local entity that points to the owner (optional)*/
	private String local_property;
	/**
	 * @return the entity_id
	 */
	public String getEntity_id() {
		return entity_id;
	}
	/**
	 * @param entityId the entity_id to set
	 */
	public void setEntity_id(String entityId) {
		entity_id = entityId;
	}
	/**
	 * @return the entity_property
	 */
	public String getEntity_property() {
		return entity_property;
	}
	/**
	 * @param entityProperty the entity_property to set
	 */
	public void setEntity_property(String entityProperty) {
		entity_property = entityProperty;
	}
	/**
	 * @return the local_property
	 */
	public String getLocal_property() {
		return local_property;
	}
	/**
	 * @param localProperty the local_property to set
	 */
	public void setLocal_property(String localProperty) {
		local_property = localProperty;
	}
}
