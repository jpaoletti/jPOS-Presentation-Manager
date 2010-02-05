/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
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


import org.jpos.ee.DB;
import org.jpos.ee.pm.security.SECUser;
import org.jpos.ee.pm.struts.EntityContainer;

/**This interface allows the programmer to defines some code to execute before or after any operation 
 * execution. if an entity is not persistent (look at {@link Entity#isPersistent()}) this is the place
 * to put code to give "life" to the operations over the entity.
 * */
public interface OperationContext {
	
    /**This method is executed before trying to execute the main method of the operation 
     * @param db The DataBase
     * @param user The logged user
     * @param entity_container The entity container
     * @param obj The instance of the entity
     */
    public void preExecute  (DB db, SECUser user, EntityContainer entity_container, EntityInstanceWrapper obj) throws Exception;

    /**This method is executed after the main method of the operation.
     * @param db The DataBase
     * @param user The logged user
     * @param entity_container The entity container
     * @param obj The instance of the entity
     */
    public void postExecute (DB db, SECUser user, EntityContainer entity_container, EntityInstanceWrapper obj) throws Exception;
}

