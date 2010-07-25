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
 * Similar to struts ActionMessage
 * @author jpaoletti
 */
public class PMMessage {
    private String key;
    private String message;
    private String arg0;
    private String arg1;
    private String arg2;
    private String arg3;
        
    /**
     * Constructor with a key
     * 
     * @param key The key
     */
    public PMMessage(String key){
        
    }

    /**
     * String representation
     * @return
     */
    @Override
    public String toString() {
        return "PMMessage{" + "key=" + key + ", message=" + message + '}';
    }

    /**
     * Helper constructor
     * @param key
     * @param message
     */
    public PMMessage(String key, String message) {
        super();
        this.message = message;
    }

    /**
     * Helper constructor
     * @param key
     * @param message
     * @param arg0
     */
    public PMMessage(String key, String message, String arg0) {
        super();
        this.message = message;
        this.arg0 = arg0;
    }

    /**
     * Helper constructor
     * @param key
     * @param message
     * @param arg0
     * @param arg1
     */
    public PMMessage(String key, String message, String arg0, String arg1) {
        super();
        this.message = message;
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    /**
     * Helper constructor
     * @param key
     * @param message
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public PMMessage(String key, String message, String arg0, String arg1, String arg2) {
        super();
        this.message = message;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    /**
     * Helper constructor
     * @param key
     * @param message
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public PMMessage(String key, String message, String arg0, String arg1, String arg2,    String arg3) {
        super();
        this.message = message;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param arg0 the arg0 to set
     */
    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }

    /**
     * @return the arg0
     */
    public String getArg0() {
        return arg0;
    }

    /**
     * @param arg1 the arg1 to set
     */
    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    /**
     * @return the arg1
     */
    public String getArg1() {
        return arg1;
    }

    /**
     * @param arg2 the arg2 to set
     */
    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    /**
     * @return the arg2
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * @param arg3 the arg3 to set
     */
    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    /**
     * @return the arg3
     */
    public String getArg3() {
        return arg3;
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
