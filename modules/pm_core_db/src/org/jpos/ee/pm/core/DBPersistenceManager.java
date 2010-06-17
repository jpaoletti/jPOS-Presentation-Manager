package org.jpos.ee.pm.core;

import javax.servlet.ServletException;

import org.hibernate.Transaction;
import org.jpos.ee.DB;

public class DBPersistenceManager implements PersistenceManager {
    
    public void commit(PMContext ctx, Object transaction) throws Exception {
        ((Transaction) transaction).commit();
    }

    public void finish(PMContext ctx) throws Exception{
        final DB db = (DB)ctx.get(PM_DB);
        if(db!=null) db.close();
    }

    public void init(PMContext ctx) throws Exception{
        try {
            DB db = new DB(PMLogger.getLog());
            db.open();
            ctx.put(PM_DB, db);
        } catch (Exception e) {
            PMLogger.error(e);
            throw new ServletException(e);
        }
    }

    public void rollback(PMContext ctx, Object transaction) throws Exception{
        ((Transaction) transaction).rollback();
        final DB db = (DB) ctx.get(PM_DB);
        db.close();
        db.open();
    }

    public Object startTransaction(PMContext ctx) throws Exception{
        final DB db = (DB) ctx.get(PM_DB);
        return db.beginTransaction();
    }
}