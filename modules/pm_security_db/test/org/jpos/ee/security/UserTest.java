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
package org.jpos.ee.security;

import java.util.Date;

import org.hibernate.Transaction;
import org.jpos.ee.DB;
import org.jpos.ee.pm.security.*;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testInsert() throws Exception {
        DB db = new DB();
        db.open();
        assertNotNull (db.session());
        Transaction tx = db.open().beginTransaction();
        
        SECPermission p = new SECPermission();
        p.setName(SECPermission.LOGIN);
        db.save(p);

        SECPermission p2 = new SECPermission();
        p2.setName(SECPermission.USER_ADMIN);
        db.save(p2);
        
        SECUserGroup group = new SECUserGroup();
        group.setActive(true);
        group.setCreation(new Date());
        group.setDescription("Super Administration");
        group.setName("Administrators");
        group.grant(p);
        group.grant(p2);
        db.save(group);

        SECUser user = new SECUser();
        user.setNick ("admin");
        user.setPassword ("66d4aaa5ea177ac32c69946de3731ec0"); //test
        user.setName ("Administrator");
        user.setActive (true);
        user.getGroups().add(group);
        
        db.save(user);
        tx.commit();
    }
}
