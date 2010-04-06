package org.jpos.ee.pm.core;

import org.jpos.ee.Constants;

public interface PersistenceManager extends Constants {
    public void init(PMContext ctx)throws Exception;
    public void finish(PMContext ctx)throws Exception;
    public Object startTransaction(PMContext ctx) throws Exception;
    public void commit(PMContext ctx, Object transaction)throws Exception;
    public void rollback(PMContext ctx, Object transaction)throws Exception;
}
