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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.transaction.Context;

/**
 *
 * @author jpaoletti
 */
public class PMSession extends Context{
    private String sessionId;
    private PMSecurityUser user;
    private Menu menu;
    private final Map<String,EntityContainer> containers = new HashMap<String, EntityContainer>();
    private Date lastAccess;
    
    public PMSession(String id) {
        this.sessionId = id;
    }

    public Collection<EntityContainer> getContainers(){
        return containers.values();
    }

    public void setContainer(String entityId, EntityContainer container){
        containers.put(entityId, container);
    }

    public EntityContainer getContainer(String entityId){
        return containers.get(entityId);
    }
    
    public PMSecurityUser getUser() {
        return user;
    }

    public void setUser(PMSecurityUser user) {
        this.user = user;
    }

    public String getId() {
        return sessionId;
    }

    public void setId(String id) {
        this.sessionId = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

}
