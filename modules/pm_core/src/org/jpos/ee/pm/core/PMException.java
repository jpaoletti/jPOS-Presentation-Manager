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
 * A generic expection for Presentation Manager engine.
 * 
 * @author jpaoletti
 */
public class PMException extends Exception {

    private String key;
    private static final long serialVersionUID = -1685585143991954053L;

    /**
     *
     * @param key
     */
    public PMException(String key) {
        setKey(key);
    }

    /**
     *
     */
    public PMException() {
        super();
    }

    /**
     *
     * @param nested
     */
    public PMException(Throwable nested) {
        super(nested);
    }

    /**
     *
     * @param s
     * @param nested
     */
    public PMException(String s, Throwable nested) {
        super(s, nested);
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
}
