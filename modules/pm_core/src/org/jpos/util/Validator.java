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

package org.jpos.util;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Validator {
    private static final Log log = LogFactory.getLog (Validator.class);
    private static final ResourceBundle bundle = 
        ResourceBundle.getBundle(Validator.class.getName());
    private static final Map patterns = new HashMap();

    public static boolean validate (String patternId, String value) {
        if (value == null)
            return false;
        Pattern p = (Pattern) patterns.get (patternId);
        if (p == null) {
            String patternString = bundle.getString (patternId);
            p = Pattern.compile (patternString);
            patterns.put (patternId, p);    // cache it
        }
        Matcher m = getPattern (patternId).matcher (value);
        return m.matches();
    }
    public static boolean matches (String pattern, String value) {
        Pattern p = Pattern.compile (pattern);
        Matcher m = p.matcher (value);
        return m.matches();
    }
    public static boolean isName (String s) {
        return validate ("name", s);
    }
    public static boolean isQuery (String s) {
        return validate ("query", s);
    }
    public static boolean isAlpha (String s) {
        return validate ("alpha", s);
    }
    public static boolean isAlpha (String s, int len) {
        return validate ("alpha", s) && s.length() <= len;
    }
    public static boolean isNick (String s) {
        return validate ("nick", s);
    }
    public static boolean isLong (String s) {
        if (s == null)
            return false;
        try {
            Long.parseLong (s.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isDecimal (String s) {
        return validate ("decimal", s);
    }
    public static boolean isState (String s) {
        return validate ("state", s);
    }
    public static boolean isBoolean(String s) {
        return validate ("boolean", s);
    }
    private static Pattern getPattern (String id) {
        Pattern p = (Pattern) patterns.get (id);
        if (p == null) {
            synchronized (patterns) {
                p = (Pattern) patterns.get (id);
                if (p == null) {
                    String s = bundle.getString (id);
                    p = Pattern.compile (s);
                    patterns.put (id, p);    // cache it
                }
            }
        }
        return p;
    }
}

