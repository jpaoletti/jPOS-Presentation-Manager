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

import java.util.List;

import org.jpos.ee.pm.core.PMContext;

public interface PMSecurityConnector{
    
    public void setService(PMSecurityService service);
    public void setContext(PMContext ctx);
    
    public PMSecurityUser authenticate(String username, String password)throws PMSecurityException;
    public void changePassword(String username, String password, String newpassword) throws PMSecurityException;
    
    public PMSecurityUser getUser(String username)              throws PMSecurityException;
    public List<PMSecurityUser> getUsers()                      throws PMSecurityException;
    public void addUser(PMSecurityUser user)                    throws PMSecurityException;
    public void updateUser(PMSecurityUser user)                 throws PMSecurityException;
    public void removeUser(PMSecurityUser object)                       throws PMSecurityException;
    
    public PMSecurityUserGroup getGroup(String id)              throws PMSecurityException;
    public List<PMSecurityUserGroup> getGroups()                throws PMSecurityException;
    public void addGroup(PMSecurityUserGroup group)             throws PMSecurityException;
    public void updateGroup(PMSecurityUserGroup group)          throws PMSecurityException;
    public void removeGroup(PMSecurityUserGroup group)          throws PMSecurityException;
    
    public PMSecurityProfile getProfile(String id)              throws PMSecurityException;
    public List<PMSecurityProfile> getProfiles()                throws PMSecurityException;
    public void addProfile(PMSecurityProfile profile)           throws PMSecurityException;
    public void updateProfile(PMSecurityProfile profile)        throws PMSecurityException;
    public void removeProfile(PMSecurityProfile profile)        throws PMSecurityException;
    
    public List<PMSecurityPermission> getPermissions()          throws PMSecurityException;

}