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
import java.util.List;
import org.jpos.ee.pm.security.core.PMSecurityUser;

import org.jpos.transaction.Context;
import org.jpos.util.Log;

/**
 * An extension of the org.jpos.transaction.Context class with some helpers
 * for PM.
 */
public class PMContext extends Context {

    private String sessionId;
    public static final String PM_ERRORS = "PM_ERRORS";

    public PMContext(String sessionId) {
        this.sessionId = sessionId;
        setErrors(new ArrayList<PMMessage>());
    }

    public PMContext() {
        this("");
    }

    /**
     * @return the errors
     */
    public List<PMMessage> getErrors() {
        return (List<PMMessage>) get(PM_ERRORS);
    }

    /**
     * @param errors the errors to set
     */
    public final void setErrors(List<PMMessage> errors) {
        put(PM_ERRORS, errors);
    }

    /**
     * Getter for PM singleton
     * @return
     */
    public PresentationManager getPresentationManager() {
        return PresentationManager.pm;
    }

    /**
     * Return the persistance manager of the PM
     * @return PersistenceManager
     */
    public PersistenceManager getPersistanceManager() {
        return getPresentationManager().getPersistenceManager();
    }

    /**
     * Return the PM log
     * @return
     */
    public Log getLog() {
        return getPresentationManager().getLog();
    }

    /**
     * @param entityContainer the entity_container to set
     */
    public void setEntityContainer(EntityContainer entityContainer) {
        put(PMCoreObject.PM_ENTITY_CONTAINER, entityContainer);
    }

    /**
     * @return the entity_container
     * @throws PMException
     */
    public EntityContainer getEntityContainer() throws PMException {
        EntityContainer entityContainer = (EntityContainer) get(PMCoreObject.PM_ENTITY_CONTAINER);
        if (entityContainer == null) {
            PresentationManager.getPm().error("Entity container not found");
            throw new PMException("pm_core.entity.not.found");
        }
        return entityContainer;
    }

    /**
     * Retrieve the container with the given id from session
     *
     * @param id The entity id
     * @return The container
     * @throws PMException when no container was found
     */
    public EntityContainer getEntityContainer(String id) throws PMException {
        EntityContainer ec = (EntityContainer) getPMSession().getContainer(id);
        if (ec == null) {
            throw new PMException("pm_core.entity.not.found");
        }
        return ec;
    }

    /**
     * Returns the entity container 
     * @param ignorenull If true, does not throws an exception on missing container
     * @return The container
     * @throws PMException
     */
    public EntityContainer getEntityContainer(boolean ignorenull) throws PMException {
        EntityContainer entityContainer = (EntityContainer) get(PMCoreObject.PM_ENTITY_CONTAINER);
        if (ignorenull) {
            return entityContainer;
        }
        if (entityContainer == null) {
            throw new PMException("pm_core.entity.not.found");
        }
        return entityContainer;
    }

    /**
     * Informs if there is a container in the context
     *
     * @return true if there is a container in the context
     */
    public boolean hasEntityContainer() {
        EntityContainer entityContainer = (EntityContainer) get(PMCoreObject.PM_ENTITY_CONTAINER);
        return entityContainer != null;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        put(PMCoreObject.PM_OPERATION, operation);
    }

    /**
     * @return the operation
     */
    public Operation getOperation() {
        return (Operation) get(PMCoreObject.PM_OPERATION);
    }

    /**
     * Return the entity in the container
     * @return The entity
     * @throws PMException
     */
    public Entity getEntity() throws PMException {
        return getEntityContainer().getEntity();
    }

    /**
     * Return the list of the container
     * @return The list
     * @throws PMException
     */
    public PaginatedList getList() throws PMException {
        return getEntityContainer().getList();
    }

    /**
     * Return the selected item of the container
     * @return The EntityInstanceWrapper
     * @throws PMException
     */
    public EntityInstanceWrapper getSelected() throws PMException {
        return getEntityContainer().getSelected();
    }

    /**
     * Indicate if there is a container with an entity
     * 
     * @return
     */
    public boolean hasEntity() {
        try {
            return (hasEntityContainer() && getEntityContainer().getEntity() != null);
        } catch (PMException e) {
            return false;
        }
    }

    public PMSession getPMSession() {
        return getPresentationManager().getSession(getSessionId());
    }

    public String getSessionId() {
        return sessionId;
    }

    /**Getter for the logged user
     * @return The user
     */
    public PMSecurityUser getUser() {
        if (getPMSession() == null) {
            return null;
        }
        return getPMSession().getUser();
    }

    /**Indicates if there is a user online
     * @return True if there is a user online
     */
    public boolean isUserOnLine() {
        return (getUser() != null);
    }

    public Object getParameter(String paramid) {
        final Object v = get("param_" + paramid);
        if (v == null) {
            return null;
        } else {
            if (v instanceof String[]) {
                String[] s = (String[]) v;
                if (s.length == 1) {
                    return s[0];
                } else {
                    return s;
                }
            }
            return v;
        }
    }

    public Object[] getParameters(String paramid) {
        return (Object[]) get("param_" + paramid);
    }

    /**
     * Getter for a boolean value.
     * @param key The key
     * @param def Default value if there is no item at key
     * @return A boolean
     */
    public boolean getBoolean(String key, boolean def) {
        try {
            if (!contains(key)) {
                return def;
            }
            return (Boolean) get(key);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Obtains a pair based on the given key. In there is no key, this method
     * returns null
     *
     * @param key The key
     * @return the ContextPair for the given key.
     */
    public ContextPair getPair(String key) {
        if (!this.contains(key)) {
            return null;
        }
        return new ContextPair(key, get(key));
    }

    /**
     * Puts the key/value pair from each pair into the context
     * @param pair The pair
     */
    public void put(ContextPair... pairs) {
        if (pairs != null) {
            for (int i = 0; i < pairs.length; i++) {
                ContextPair pair = pairs[i];
                this.put(pair.getKey(), pair.getValue());
            }
        }
    }

    /**
     * Indicate if the key is present.
     *
     * @param key The key
     * @return true if value asociated to the key is not null
     */
    public boolean contains(final String key) {
        return get(key) != null;
    }

    /**
     * Build a new pair
     */
    public ContextPair newPair(final String key, final Object value) {
        return new ContextPair(key, value);
    }

    /**
     * Helper class to simplify context pairs to be moved from one context to
     * another.
     *
     */
    public static class ContextPair {

        private String key;
        private Object value;

        public ContextPair(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
