package org.jpos.ee.pm.security.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.jpos.ee.DB;
import org.jpos.ee.pm.core.DBPersistenceManager;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.security.MD5_hex;
import org.jpos.ee.pm.security.SECPermission;
import org.jpos.ee.pm.security.SECUser;
import org.jpos.ee.pm.security.SECUserGroup;
import org.jpos.ee.pm.security.core.GroupAlreadyExistException;
import org.jpos.ee.pm.security.core.InvalidPasswordException;
import org.jpos.ee.pm.security.core.InvalidUserException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityPermission;
import org.jpos.ee.pm.security.core.PMSecurityProfile;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.security.core.PMSecurityUserGroup;
import org.jpos.ee.pm.security.core.UserAlreadyExistException;
import org.jpos.ee.pm.security.core.UserNotActiveException;
import org.jpos.ee.pm.security.core.UserNotFoundException;
import org.jpos.util.Log;

public class PMSecurityDBConnector implements PMSecurityConnector {

    protected PMSecurityService service;
    protected PMContext ctx;

    public PMSecurityDBConnector() {
    }

    public void setService(PMSecurityService service) {
        this.service = service;
    }

    protected DB getDb() {
        return (DB) ctx.get(DBPersistenceManager.PM_DB);
    }

    protected Log getLog() {
        return service.getLog();
    }

    /**Here i get plain password and i must encrypt it before compare*/
    public PMSecurityUser authenticate(String username, String password) throws PMSecurityException {
        PMSecurityUser user = getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.isDeleted()) {
            throw new UserNotFoundException();
        }
        if (!user.isActive()) {
            throw new UserNotActiveException();
        }
        if (user.getPassword().compareTo(this.encryptPassword(username, password)) != 0) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    public void changePassword(String username, String password, String newpassword) throws PMSecurityException {
        DB db = getDb();
        try {
            SECUser user = getDBUser(username);
            if (password != null) {
                authenticate(username, password);
                checkUserRules(username, password);
            }
            user.setPassword(encryptPassword(username, newpassword));
            db.save(user);
        } catch (PMSecurityException e) {
            throw e;
        } catch (Exception e) {
            getLog().error(e);
            throw new PMSecurityException("unespected.error");
        }
    }

    protected void checkUserRules(String username, String password) throws PMSecurityException {
        if (username == null) {
            throw new InvalidUserException();
        }
        if (password == null) {
            throw new InvalidPasswordException();
        }
        //TODO Check rules
    }

    protected String encryptPassword(String username, String password) {
        return ((new MD5_hex()).calcMD5(username + password));
    }

    public PMSecurityUser getUser(String username) throws PMSecurityException {
        return convert(getDBUser(username));
    }

    public SECUser getDBUser(String username) {
        DB db = getDb();
        SECUser u = null;
        try {
            u = (SECUser) db.session().createCriteria(SECUser.class).add(Restrictions.eq("nick", username)).uniqueResult();

            if (u != null) {
                db.session().refresh(u);
            }

        } catch (Exception e) {
            getLog().error(e);
        }
        return u;
    }

    public List<PMSecurityUser> getUsers() throws PMSecurityException {
        List<PMSecurityUser> result = new ArrayList<PMSecurityUser>();
        DB db = getDb();
        try {
            List<SECUser> users = db.session().createCriteria(SECUser.class).list();
            for (SECUser u : users) {
                result.add(convert(u));
            }
        } catch (Exception e) {
            getLog().error(e);
        }
        return result;
    }

    public void addUser(PMSecurityUser user) throws PMSecurityException {
        DB db = getDb();
        try {
            if (getDBUser(user.getUsername().toLowerCase()) != null) {
                throw new UserAlreadyExistException();
            }
            checkUserRules(user.getUsername(), user.getPassword());
            SECUser secuser = unconvert(null, user);
            secuser.setPassword(encryptPassword(user.getUsername(), user.getPassword()));

            db.session().save(secuser);
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    public void updateUser(PMSecurityUser user) throws PMSecurityException {
        DB db = getDb();
        try {
            checkUserRules(user.getUsername(), user.getPassword());
            SECUser secuser = getDBUser(user.getUsername());
            secuser = unconvert(secuser, user);
            db.session().update(secuser);
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    public PMSecurityUserGroup getGroup(String groupname) throws PMSecurityException {
        return convert(getDBGroup(groupname));
    }

    public SECUserGroup getDBGroup(String groupname) {
        DB db = getDb();
        SECUserGroup g = null;
        try {
            g = (SECUserGroup) db.session().createCriteria(SECUserGroup.class).add(Restrictions.eq("name", groupname)).uniqueResult();
        } catch (Exception e) {
            getLog().error(e);
        }
        return g;
    }

    public List<PMSecurityUserGroup> getGroups() throws PMSecurityException {
        DB db = getDb();
        List<PMSecurityUserGroup> groups = new ArrayList<PMSecurityUserGroup>();
        try {
            List<SECUserGroup> ug = db.session().createCriteria(SECUserGroup.class).list();
            for (SECUserGroup g : ug) {
                groups.add(convert(g));
            }
        } catch (Exception e) {
            getLog().error(e);
        } finally {
        }
        return groups;
    }

    public void addGroup(PMSecurityUserGroup group) throws PMSecurityException {
        DB db = getDb();
        try {
            if (getDBGroup(group.getName()) != null) {
                throw new GroupAlreadyExistException();
            }

            SECUserGroup secuserg = unconvert(null, group);

            db.session().save(secuserg);
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    public void updateGroup(PMSecurityUserGroup group) throws PMSecurityException {
        DB db = getDb();
        try {
            SECUserGroup secuserg = getDBGroup(group.getName());
            db.session().refresh(secuserg);
            secuserg = unconvert(secuserg, group);
            db.session().update(secuserg);
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    public List<PMSecurityPermission> getPermissions() throws PMSecurityException {
        List<PMSecurityPermission> perms = new ArrayList<PMSecurityPermission>();
        DB db = getDb();
        try {
            List<SECPermission> ps = db.session().createCriteria(SECPermission.class).list();
            for (SECPermission p : ps) {
                perms.add(convert(p));
            }
        } catch (Exception e) {
            getLog().error(e);
        } finally {
        }
        return perms;
    }

    /*Converters*/
    protected PMSecurityUser convert(SECUser u) throws PMSecurityException {
        PMSecurityUser user = new PMSecurityUser();
        load(u, user);
        return user;
    }
    protected void load(SECUser u, PMSecurityUser user) throws PMSecurityException {
        user.setActive(u.isActive());
        user.setChangePassword(u.isChangePassword());
        user.setDeleted(u.isDeleted());
        user.setEmail(u.getEmail());
        user.setName(u.getName());
        user.setPassword(u.getPassword());
        user.setUsername(u.getNick());
        for (SECUserGroup g : u.getGroups()) {
            user.getGroups().add(convert(g));
        }
    }

    protected PMSecurityUserGroup convert(SECUserGroup g) {
        if (g == null) {
            return null;
        }
        PMSecurityUserGroup group = new PMSecurityUserGroup();
        group.setActive(g.isActive());
        group.setDescription(g.getDescription());
        group.setName(g.getName());
        for (SECPermission p : g.getPermissions()) {
            group.getPermissions().add(convert(p));
        }

        return group;
    }

    protected PMSecurityPermission convert(SECPermission p) {
        if (p == null) {
            return null;
        }
        PMSecurityPermission perm = new PMSecurityPermission();
        perm.setDescription(p.getDescription());
        perm.setName(p.getName());
        return perm;
    }

    protected SECUser unconvert(SECUser secuser, PMSecurityUser u) {
        if (u == null) {
            return null;
        }
        SECUser user = secuser;
        if (secuser == null) {
            user = new SECUser();
        }
        unload(u, secuser, user);
        return user;
    }

    protected void unload(PMSecurityUser u, SECUser secuser, SECUser output) {
        output.getGroups().clear();
        output.setActive(u.isActive());
        output.setChangePassword(u.isChangePassword());
        output.setDeleted(u.isDeleted());
        output.setEmail(u.getEmail());
        output.setName(u.getName());
        if (secuser == null) {
            output.setNick(u.getUsername().toLowerCase());
        }
        for (PMSecurityUserGroup g : u.getGroups()) {
            output.getGroups().add(getDBGroup(g.getName()));
        }
    }

    protected SECUserGroup unconvert(SECUserGroup secgroup, PMSecurityUserGroup g) {
        if (g == null) {
            return null;
        }
        SECUserGroup group = secgroup;
        if (secgroup == null) {
            group = new SECUserGroup();
        }
        group.setActive(g.isActive());
        group.setDescription(g.getDescription());
        if (secgroup == null) {
            group.setName(g.getName());
        }
        for (PMSecurityPermission p : g.getPermissions()) {
            group.getPermissions().add(getDBPerm(p.getName()));
        }
        return group;
    }

    protected SECPermission getDBPerm(String name) {
        DB db = getDb();
        SECPermission p = null;
        try {
            p = (SECPermission) db.session().createCriteria(SECPermission.class).add(Restrictions.eq("name", name)).uniqueResult();
            if (p != null) {
                db.session().refresh(p);
            }
        } catch (Exception e) {
            getLog().error(e);
        }
        return p;
    }

    protected SECPermission unconvert(PMSecurityPermission p) {
        if (p == null) {
            return null;
        }
        SECPermission perm = new SECPermission();
        perm.setDescription(p.getDescription());
        perm.setName(p.getName());
        return perm;
    }

    public void setContext(PMContext ctx) {
        this.ctx = ctx;
    }

    public void addProfile(PMSecurityProfile profile) throws PMSecurityException {
        // TODO Auto-generated method stub
    }

    public PMSecurityProfile getProfile(String id) throws PMSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PMSecurityProfile> getProfiles() throws PMSecurityException {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeGroup(PMSecurityUserGroup group)
            throws PMSecurityException {
        DB db = getDb();
        db.session().delete(getDBGroup(group.getName()));
    }

    public void removeProfile(PMSecurityProfile profile)
            throws PMSecurityException {
        // TODO Auto-generated method stub
    }

    public void updateProfile(PMSecurityProfile profile)
            throws PMSecurityException {
        // TODO Auto-generated method stub
    }

    public void removeUser(PMSecurityUser object) throws PMSecurityException {
        DB db = getDb();
        db.session().delete(getDBUser(object.getUsername()));
    }
}
