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
package org.jpos.ee.pm.security.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PMSecurityProfile implements Serializable{
    private static final long serialVersionUID = 8383915466636478529L;
    private Integer id;
    private String name;
    private String description;
    private boolean active;
    private List<PMSecurityPermission> permissions;
    
    public PMSecurityProfile() {
        setPermissions(new ArrayList<PMSecurityPermission>());
    }

    public String toString() {
        return getName();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<PMSecurityPermission> permissions) {
        this.permissions = permissions;
    }
    /**
     * @return the permissions
     */
    public List<PMSecurityPermission> getPermissions() {
        return permissions;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PMSecurityProfile other = (PMSecurityProfile) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public PMSecurityPermission getPermission (String name) {
        for(PMSecurityPermission p : getPermissions()){
            if(name!=null && p!=null && name.compareTo(p.getName()) == 0) return p;
        }
        return null;
    }
    
    public boolean hasPermission (String permName) {
        return getPermission(permName)!= null;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
}

