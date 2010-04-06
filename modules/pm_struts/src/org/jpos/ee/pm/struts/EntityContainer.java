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
package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;

/**
 * @author jpaoletti
 * 
 * This class encapsulate an entity, its list and everything associated to an entity. An instance
 * of this class is inserted in web session under demand and stay in session for fast reference.
 * 
 */
public class EntityContainer {
    private String id;
    private String sid;
    private Entity entity;
    private PMList list;
    private EntityInstanceWrapper selected;
    private boolean selectedNew;
    private EntityFilter filter;
    
    public EntityContainer(Entity entity, String sid) {
        super();
        setEntity(entity);
        setSid(sid);
        setId(buildId(sid, entity.getId()));
        setSelectedNew(false);
    }
    
    public static String buildId(String sid, String eid){
        //return sid.substring(0,20) + eid.hashCode() + sid.substring(20);
        return eid;
    }
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Entity getEntity() {
        return entity;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    public PMList getList() {
        return list;
    }
    public void setList(PMList list) {
        this.list = list;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getSid() {
        return sid;
    }

    public void setSelected(EntityInstanceWrapper selected) {
        this.selected = selected;
        setSelectedNew(false);
    }

    public EntityInstanceWrapper getSelected() {
        return selected;
    }

    public void setSelectedNew(boolean new_) {
        this.selectedNew = new_;
    }

    public boolean isSelectedNew() {
        return selectedNew;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(EntityFilter filter) {
        this.filter = filter;
    }

    /**
     * @return the filter
     */
    public EntityFilter getFilter() {
        return filter;
    }
    
}
