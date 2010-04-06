package org.jpos.ee.pm.core;

/**
 * This class replace the old service property "ignoreDB". Just use this class
 * in "persistence-manager"
 * 
 * @author jpaoletti
 *
 */
public class VoidPersistenceManager implements PersistenceManager {

    public void commit(PMContext ctx, Object transaction) throws Exception {

    }

    public void finish(PMContext ctx) throws Exception {

    }

    public void init(PMContext ctx) throws Exception {

    }

    public void rollback(PMContext ctx, Object transaction) throws Exception {
    
    }

    public Object startTransaction(PMContext ctx) throws Exception {
        return null;
    }
}