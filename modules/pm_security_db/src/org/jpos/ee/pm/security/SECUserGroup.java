package org.jpos.ee.pm.security;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

public class SECUserGroup {
    private long id;
    private String name;
    private String description;
    private Date creation;
    private boolean active;
    private Set<SECPermission> permissions;
    
    public String toString() {
        return name;
    }
    
    public int hashCode() {
        return (int) id;
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SECUserGroup) ) return false;
        SECUserGroup castOther = (SECUserGroup) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }
    
    public SECUserGroup() {
        super();
        permissions = new LinkedHashSet<SECPermission> ();
    }
    
    public SECPermission getPermission (String name) {
        for(SECPermission p : permissions){
            if(name!=null && p!=null && name.compareTo(p.getName()) == 0) return p;
        }
        return null;
    }
    

    public boolean hasPermission (String permName) {
        return getPermission(permName)!= null;
    }
    
    public void grant (SECPermission perm) {
        permissions.add (perm);
    }
    public void revoke (String perm) {
        permissions.remove (getPermission(perm));
    }
    public void revokeAll () {
        permissions.clear ();
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getCreation() {
        return creation;
    }
    public void setCreation(Date creation) {
        this.creation = creation;
    }
    public Set<SECPermission> getPermissions() {
        return permissions;
    }
    public void setPermissions(Set<SECPermission> permissions) {
        this.permissions = permissions;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }
}

