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
package org.jpos.ee.pm.core;
import org.hibernate.Transaction;
import org.jpos.ee.DB;

public class DBPersistenceManager implements PersistenceManager {

    public static final String PM_DB = "DB";

    public void commit(PMContext ctx, Object transaction) throws Exception {
        ((Transaction) transaction).commit();
    }

    public void finish(PMContext ctx) throws Exception {
        final DB db = (DB) ctx.get(PM_DB);
        if (db != null) {
            db.close();
        }
    }

    public void init(PMContext ctx) throws Exception {
        try {
            DB db = new DB(ctx.getLog());
            db.open();
            ctx.put(PM_DB, db);
        } catch (Exception e) {
            ctx.getPresentationManager().error(e);
            throw new PMException(e);
        }
    }

    public void rollback(PMContext ctx, Object transaction) throws Exception {
        ((Transaction) transaction).rollback();
        final DB db = (DB) ctx.get(PM_DB);
        db.close();
        db.open();
    }

    public Object startTransaction(PMContext ctx) throws Exception {
        final DB db = (DB) ctx.get(PM_DB);
        return db.beginTransaction();
    }
}
