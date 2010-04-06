package org.jpos.ee.pm.core;

import javax.servlet.ServletException;

import org.hibernate.Transaction;
import org.jpos.ee.DB;

public class DBPersistenceManager implements PersistenceManager {
    
    public void commit(PMContext ctx, Object transaction) throws Exception {
        ((Transaction) transaction).commit();
    }

    public void finish(PMContext ctx) throws Exception{
        if(ctx.get(PM_DB)!=null){
            ((DB)ctx.get(PM_DB)).close();
        }
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
        ((DB)ctx.get(PM_DB)).close();
        ((DB)ctx.get(PM_DB)).open();
    }

    public Object startTransaction(PMContext ctx) throws Exception{
        return ((DB)ctx.get(PM_DB)).beginTransaction();
    }
}