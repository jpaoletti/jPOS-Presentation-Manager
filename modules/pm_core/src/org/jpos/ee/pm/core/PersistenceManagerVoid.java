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

/**
 * This class replace the old service property "ignoreDB". Just use this class
 * in "persistence-manager"
 * 
 * @author jpaoletti
 *
 */
public class PersistenceManagerVoid implements PersistenceManager {

    /**
     * Inherited. Just do nothing
     * @param ctx
     * @param transaction
     * @throws Exception
     */
    public void commit(PMContext ctx, Object transaction) throws Exception {

    }

    /**
     * Inherited. Just do nothing
     * @param ctx
     * @throws Exception
     */
    public void finish(PMContext ctx) throws Exception {

    }

    /**
     * Inherited. Just do nothing
     * @param ctx
     * @throws Exception
     */
    public void init(PMContext ctx) throws Exception {

    }

    /**
     * Inherited. Just do nothing
     * @param ctx
     * @param transaction
     * @throws Exception
     */
    public void rollback(PMContext ctx, Object transaction) throws Exception {
    
    }

    /**
     * Inherited. Just do nothing
     * @param ctx
     * @return
     * @throws Exception
     */
    public Object startTransaction(PMContext ctx) throws Exception {
        return null;
    }
}