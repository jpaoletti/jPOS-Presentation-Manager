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

import java.util.List;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.struts.EntityContainer;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMStrutsService;
import org.jpos.transaction.Context;

/**An extension of the org.jpos.transaction.Context class with some helpers
 * for PM.*/
public class PMContext extends Context implements Constants{
    

    /**
     * @return the errors
     */
    public List<PMMessage> getErrors() {
        return (List<PMMessage>) get(PM_ERRORS);
    }
    /**
     * @param errors the errors to set
     */
    public void setErrors(List<PMMessage> errors) {
        put(PM_ERRORS,errors);
    }
    
    public PMSecurityUser getUser(){
        PMSecurityUser user = (PMSecurityUser) get(USER);
        return user;
    }
    
    public boolean isUserOnLine() {
        return (getUser() != null);
    }
    
    
    protected PMStrutsService getPMService(){        
        return (PMStrutsService) PMEntitySupport.staticPmservice();
    }
    /**
     * @param entityContainer the entity_container to set
     */
    public void setEntityContainer(EntityContainer entityContainer) {
        put(PM_ENTITY_CONTAINER,entityContainer);
    }
    /**
     * @return the entity_container
     * @throws PMException 
     */
    public EntityContainer getEntityContainer() throws PMException {
        EntityContainer entityContainer = (EntityContainer) get(PM_ENTITY_CONTAINER);
        if(entityContainer == null){
            throw new PMException("pm_core.entity.not.found");
        }
        return entityContainer;
    }

    /**
     * @return the entity_container
     * @throws PMException 
     */
    public boolean hasEntityContainer(){
        EntityContainer entityContainer = (EntityContainer) get(PM_ENTITY_CONTAINER);
        return entityContainer != null;
    }
    /**
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        put(PM_OPERATION,operation);
    }
    /**
     * @return the operation
     */
    public Operation getOperation() {
        return (Operation)get(PM_OPERATION);
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(EntityContainer owner) {
        put(PM_OWNER,owner);
    }
    /**
     * @return the owner
     */
    public EntityContainer getOwner() {
        return (EntityContainer)get(PM_OWNER);
    }
    
    public Entity getEntity()throws PMException{
        return getEntityContainer().getEntity();
    }
    
    public PMList getList() throws PMException{
        return getEntityContainer().getList();
    }
    
    public boolean isWeak(){
        return getOwner() != null;
    }

    public EntityInstanceWrapper getSelected() throws PMException{
        return getEntityContainer().getSelected();
    }
    
    public boolean hasEntity() {
        try {
            return (hasEntityContainer() && getEntityContainer().getEntity() != null);
        } catch (PMException e) {
            return false;
        }
    }

}