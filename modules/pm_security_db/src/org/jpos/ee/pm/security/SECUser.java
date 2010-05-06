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
package org.jpos.ee.pm.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jpos.ee.Cloneable;


public class SECUser extends Cloneable implements Serializable {
    private static final long serialVersionUID = -2596321779435316577L;
    private long id;
    private String nick;
    private String password;
    private String name;
    private Set<SECUserGroup> groups;
    private Map<String,String>    props;
    private boolean deleted;
    private boolean active;
    private List<SECPasswordHistory>   passwordhistory;
    private String email;
    private boolean changePassword;
    
    public SECUser() {
        super();
        groups = new LinkedHashSet<SECUserGroup> ();
        passwordhistory = new LinkedList<SECPasswordHistory> ();
    }

    public boolean hasPermission (String permName) {
        if(permName == null) return true;
        for(SECUserGroup g : groups){
            if(g.hasPermission(permName)) return true;
        }
        return false;
    }
    
    public void logRevision(String s, SECUser me) {

    }

    public boolean belongsTo(long gid){
        for(SECUserGroup g : groups){
            if(g.getId() == gid) return true;
        }
        return false;
    }
    
    public String getNick() {
        return nick;
    }
    public void setNick (String nick) {
        this.nick = nick;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId (long id) {
        this.id = id;
    }
    public void setPassword (String password) {
        this.password = password;
    }
    public String getPassword () {
        return password;
    }
    public void setDeleted (boolean deleted) {
        this.deleted = deleted;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setActive (boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }   

    public void setPasswordhistory (List<SECPasswordHistory> passwordhistory) {
        this.passwordhistory = passwordhistory;
    }
    public List<SECPasswordHistory> getPasswordhistory () {
        return passwordhistory;
    }        
    public void addPasswordHistoryValue (String passwordhistoryhash) {
        passwordhistory.add (new SECPasswordHistory(passwordhistoryhash));
    }
    public boolean containsPasswordHistoryValue (String passwordhistoryhash) {
        return passwordhistoryhash != null ? 
            passwordhistory.contains (
                new SECPasswordHistory (passwordhistoryhash)
            ) : false;
    }
    public void prunePasswordHistoryValue (int numPasswordHistoryValue) {
        while (passwordhistory.size() > numPasswordHistoryValue) {
            passwordhistory.remove(0);
         }               
    }
    
    public void setProps (Map<String,String> props) {
        this.props = props;
    }
    public Map<String,String> getProps () {
        return (props = props == null ? new HashMap<String,String> () : props);
    }
    public void set (String prop, String value) {
        getProps().put (prop, value);
    }
    public String get (String prop) {
        return (String) getProps().get (prop);
    }
    public String get (String prop, String defValue) {
        String value = (String) getProps().get (prop);
        return value == null ? defValue : value;
    }
    public boolean hasProperty (String prop) {
        return (String) getProps().get (prop) != null ? true : false; 
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("nick", getNick())
            .toString();
    }
    public boolean equals(Object other) {
        if ( !(other instanceof SECUser) ) return false;
        SECUser castOther = (SECUser) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
    
    /**
     * @return "nick(id)"
     */
    public String getNickAndId() {
        StringBuffer sb = new StringBuffer (getNick());
        sb.append ('(');
        sb.append (Long.toString(getId()));
        sb.append (')');
        return sb.toString();
    }

    public Set<SECUserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<SECUserGroup> groups) {
        this.groups = groups;
    }



    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }



    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }



    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }



    public boolean isChangePassword() {
        return changePassword;
    }
}
