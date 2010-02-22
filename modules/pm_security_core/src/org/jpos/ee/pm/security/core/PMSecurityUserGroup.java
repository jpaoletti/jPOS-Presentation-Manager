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
package org.jpos.ee.pm.security.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

public class PMSecurityUserGroup implements Serializable{
	private static final long serialVersionUID = 8383915466636478529L;
	private String id;
	private String name;
	private String description;
	private boolean active;
	private List<PMSecurityPermission> permissions;
	
	public PMSecurityUserGroup() {
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
		return getName().hashCode();
	}

	public boolean equals(Object other) {
        if ( !(other instanceof PMSecurityUserGroup) ) return false;
        PMSecurityUserGroup castOther = (PMSecurityUserGroup) other;
        return new EqualsBuilder()
            .append(this.getName(), castOther.getName())
            .isEquals();
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
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}

