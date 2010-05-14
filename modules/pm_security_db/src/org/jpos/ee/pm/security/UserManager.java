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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jpos.ee.DB;
import org.jpos.ee.MD5;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.security.rules.SECRule;
import org.jpos.iso.ISOUtil;


public class UserManager {
    private DB db;
    
    public UserManager (DB db) {
        super ();
        this.db = db;
    }
    
    public SECUser chechUserExistance(String nick) throws SECException{
        SECUser u = getUserByNick (nick);
        if (u == null) throw new SECException ("pm_security.user.not.found");
        return u;
    }
    
    public void verifyUser(SECUser u) throws SECException{
        if (u.isDeleted())
            throw new SECException ("pm_security.user.not.exists");
        if (!u.isActive())
            throw new SECException ("pm_security.user.disabled");
        if (!u.hasPermission (SECPermission.LOGIN)) {
            throw new SECException ("pm_security.user.no.login.perm");
        }
    }

    /**
     * @param u The user
     * @throws SECException
     * @throws ParseException
     */
    public void checkUserLock(SECUser u) throws SECException, ParseException {
        Transaction tx;
        if (u.hasProperty ("Lockout")) {
            if (u.get("Lockout").equalsIgnoreCase("Y")) {
                if (u.hasProperty("LockUntil")) {
                    if ((u.get("LockUntil")).equals("0"))
                        throw new SECException ("pm_security.user.locked");                                       
                            
                     Calendar calLockUntil = Calendar.getInstance();  
                     Calendar cal = Calendar.getInstance();  
                     DateFormat df = DateFormat.getDateTimeInstance();                                                                                               
                     calLockUntil.setTime (df.parse(u.get("LockUntil")));
                                                            
                     if (cal.after(calLockUntil)) {
                        tx = db.session().beginTransaction();
                        u.set("Lockout","");
                        u.set("LockUntil","");
                        db.session().update (u);
                        tx.commit ();
                     } else {
                        throw new SECException ("pm_security.user.locked");   
                     }
                } 
                else {
                    throw new SECException ("pm_security.user.locked");   
                }           
            }
        }
    }
    
    public SECUser getUserByNick (String nick, boolean includeDeleted)
        throws HibernateException
    {
        try {
            Criteria crit = db.session().createCriteria (SECUser.class)
                .add (Restrictions.eq ("nick", nick));
            if (!includeDeleted)
                crit = crit.add (Restrictions.eq ("deleted", Boolean.FALSE));
            SECUser u = (SECUser) crit.uniqueResult();
            if(u!=null)db.session().refresh(u);
            return u;
        } catch (ObjectNotFoundException e) { }
        return null;
    }
    public SECUser getUserByNick (String nick) throws HibernateException {
        return getUserByNick (nick, false);
    }
    
    /**
    * @param nick name.
     * @param seed initial seed
     * @param pass hash
     * @throws SECException if invalid user/pass
     */
    public SECUser getUserByNick (String nick, String seed, String pass)throws HibernateException, SECException{
        SECUser u = getUserByNick (nick);
        if(u==null)throw new SECException ("pm_security.user.not.found");
        try {
            assertTrue (checkPassword (u, seed, pass), "pm_security.password.invalid");
        } catch (SECException e) {
            throw new InvalidPasswordException("pm_security.password.invalid");
        }
        return u;
    }
    
    public SECUser getUser(long id){
        return (SECUser) db.session().get(SECUser.class, new Long (id));
    }
    
    public static String getHash (String userName, String pass) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance ("MD5");
            md.update (userName.getBytes());
            hash = ISOUtil.hexString (
                md.digest (pass.getBytes())
            ).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            // should never happen
        }   
        return hash;
    }
    
    /**
    * @param u The user.
     * @param seed initial seed
     * @param pass hash
     * @return true if password matches
     * @throws SECException if invalid user/pass
     */
    public boolean checkPassword (SECUser u, String seed, String pass)throws HibernateException, SECException{
        assertNotNull (pass, "pm_security.password.invalid");
        String computedPass = getComputedPassword(u.getPassword(), seed);
        return pass.equals (computedPass);
        //return true;
    }

    /**
     * @param password The password
     * @param seed The seed
     * @return Computed password
     * @throws SECException if invalid user/pass
     */
    public String getComputedPassword(String password, String seed)
            throws SECException {
        assertNotNull (seed, "pm_security.seed.invalid");
        assertNotNull (password, "pm_security.password.null");
        String computedPass = getHash (seed, password);
        return computedPass;
    }
   
    /**
     * @param password The password to encrypt.
     * @param seed The seed
     * @return Computed password
     * @throws SECException if invalid user/pass
     */
    public String getEncryptedAndComputedPassword(String password, String seed) throws SECException {
        String p = (new MD5_hex()).calcMD5(password);
        String computedPass = getComputedPassword(p, seed);
        return computedPass;
    }
   
    public void buildUser(SECUser u, String uname, String pass) throws Exception{
        //SysConfigManager cfgMrg = new SysConfigManager(db);

        checkRules(uname,pass);
        
        u.setNick (uname);
        u.setPassword ((new MD5_hex()).calcMD5(uname+pass));
        u.setActive (true);
        
        /*if (cfgMrg.get("PasswordAge")!=null) {
            int passAge = cfgMrg.getInt("PasswordAge");
            if (passAge != 0) {
                Calendar cal = Calendar.getInstance();  
                DateFormat df = DateFormat.getDateTimeInstance();
                u.set("PasswordChanged", df.format(cal.getTime()));
            }
        }

        if (cfgMrg.get("PasswordHistory")!=null)    {
            u.addPasswordHistoryValue(pass);
            u.prunePasswordHistoryValue(cfgMrg.getInt("PasswordHistory"));
        }*/
    }
    
    private void checkRules(String uname, String pass) throws Exception {
        try {
            Criteria c = db.session().createCriteria(SECRule.class);
            List<SECRule> rules = c.list();
            for(SECRule rule: rules){
                rule.validate(uname, pass);
            }
        } catch (SECException e) {
            throw e;
        } catch (Exception e) {
            PMLogger.error(e);
            throw new Exception("Could not load security rules");
        }
    }

    /**
     * @param cfgMgr
     * @param u
     *
    public void lockUser(SysConfigManager cfgMgr, SECUser u) {
        Transaction tx;
        //set - user lockout property
        tx = session.beginTransaction();
        u.set("Lockout","Y");

        /*if (cfgMgr.hasProperty("LockDuration")) {
            int lockDuration = cfgMgr.getInt("LockDuration");
            if (lockDuration != 0) {
                Calendar cal = Calendar.getInstance();  
                cal.add(Calendar.SECOND, lockDuration);                                        
                //Set Lockout until time
                DateFormat df = DateFormat.getDateTimeInstance();
                u.set("LockUntil", df.format(cal.getTime()));
                    
                SysLog syslog = new SysLog (db);
                syslog.log ("WEB", "AUDIT", SysLog.INFO, 
                   "SECUser Account: " + u.getNickAndId() +  " has been locked out"
                );
            }                                        
        }
        session.update (u);
        tx.commit ();
    }*/
    
    /**
     * @return all users
     */
    public List<SECUser> findAll () throws HibernateException {
        List<SECUser> list = (List<SECUser>)db.session().createCriteria (SECUser.class)
                .add (Restrictions.eq ("deleted", Boolean.FALSE))
                .list();
        return list;
    }
    private void assertNotNull (Object obj, String error) throws SECException {
        if (obj == null)
            throw new SECException (error);
    }
    
    private void assertTrue (boolean condition, String error)  throws SECException {
        if (!condition) throw new SECException (error);
    }

    public List<SECUserGroup> getAllGroups() {
        Criteria c = db.session().createCriteria(SECUserGroup.class);
        c.add(Restrictions.eq("active", Boolean.TRUE));
        return c.list();
    }
    
    public SECUserGroup getGroup(long id){
        try {
            return (SECUserGroup) db.session().get(SECUserGroup.class, id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public SECUserGroup getGroupByName(String name){
        Criteria c = db.session().createCriteria(SECUserGroup.class);
        c.add(Restrictions.eq("name", name));
        c.setMaxResults(1);
        try {
            return (SECUserGroup) c.uniqueResult();
        } catch (Exception e) {
            return null;
        }
         
    }

    public void delete(SECUser u, SECUser me) {
        Transaction tx = null;
        try {
            tx = db.beginTransaction();
            u.setDeleted (true);
            //u.logRevision ("deleted", me);
            db.session().update (u);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace(); //loguear mejor
            if(tx != null) tx.rollback();
        }

        
    }

    public void changePassword(SECUser u, String actualpass, String newpass, String seed) throws Exception {
        if(!checkPassword(u, seed, actualpass))
            throw new SECException( "chpass.invalid.actual" );
        
        checkRules(u.getNick(),newpass);

        MD5 md5 = new MD5();
        u.setPassword(md5.calcMD5(u.getNick() + newpass));
        u.setChangePassword(false);
    }
    
    public boolean isDbConnected(){
        return (db != null && db.session() != null && db.session().isConnected() && db.session().isOpen());
    }
}