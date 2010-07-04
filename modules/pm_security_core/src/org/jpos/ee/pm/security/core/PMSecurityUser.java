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

import java.util.ArrayList;
import java.util.List;

public class PMSecurityUser {

    private String username;
    private String password;
    private String name;
    private List<PMSecurityUserGroup> groups;
    private List<PMSecurityProfile> profiles;
    private List<PMSecurityPermission> permissions;
    private boolean deleted;
    private boolean active;
    private String email;
    private boolean changePassword;

    public PMSecurityUser() {
        init();
    }

    private void init() {
        setGroups(new ArrayList<PMSecurityUserGroup>());
        setProfiles(new ArrayList<PMSecurityProfile>());
        setPermissions(new ArrayList<PMSecurityPermission>());
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the changePassword
     */
    public boolean isChangePassword() {
        return changePassword;
    }

    /**
     * @param changePassword the changePassword to set
     */
    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<PMSecurityUserGroup> groups) {
        this.groups = groups;
    }

    /**
     * @return the groups
     */
    public List<PMSecurityUserGroup> getGroups() {
        return groups;
    }

    public boolean hasPermission(String permission) {
        if (permission == null) {
            return true;
        }
        //First we check personal permissions
        for (PMSecurityPermission p : getPermissions()) {
            if (p.getName().compareTo(permission) == 0) {
                return true;
            }
        }
        //Second we check profile permissions
        for (PMSecurityProfile p : getProfiles()) {
            if (p.hasPermission(permission)) {
                return true;
            }
        }
        //Finally, we check group permissions
        for (PMSecurityUserGroup g : getGroups()) {
            if (g.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean belongsTo(String groupname) {
        for (PMSecurityUserGroup g : getGroups()) {
            if (g.getName().compareToIgnoreCase(groupname) == 0) {
                return true;
            }
        }
        return false;
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

    /**
     * @param profiles the profiles to set
     */
    public void setProfiles(List<PMSecurityProfile> profiles) {
        this.profiles = profiles;
    }

    /**
     * @return the profiles
     */
    public List<PMSecurityProfile> getProfiles() {
        return profiles;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<String> getPermissionList() {
        List<String> result = new ArrayList<String>();
        for (PMSecurityPermission perm : getPermissions()) {
            result.add(perm.getName());
        }
        for (PMSecurityUserGroup group : getGroups()) {
            for (PMSecurityPermission perm : group.getPermissions()) {
                if (!result.contains(perm.getName())) {
                    result.add(perm.getName());
                }
            }
        }
        for (PMSecurityProfile profile : getProfiles()) {
            for (PMSecurityPermission perm : profile.getPermissions()) {
                if (!result.contains(perm.getName())) {
                    result.add(perm.getName());
                }
            }
        }
        return result;
    }
}
