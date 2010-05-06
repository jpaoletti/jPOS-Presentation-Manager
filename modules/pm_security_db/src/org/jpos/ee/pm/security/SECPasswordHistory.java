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
package org.jpos.ee.pm.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class SECPasswordHistory implements Serializable {
    private static final long serialVersionUID = 394737581295663842L;
    String value;
    public SECPasswordHistory () {
        super ();
        setValue ("");
    }
    public SECPasswordHistory (String value) {
        super ();
        setValue (value);
    }        
    public void setValue (String value) {
        this.value = value;
    }
    public String getValue () {
        return value;
    }
    public String toString () {
        return getValue ();
    } 
    public boolean equals(Object other) {
        if ( !(other instanceof SECPasswordHistory) ) return false;
        SECPasswordHistory castOther = (SECPasswordHistory) other;
        return new EqualsBuilder()
            .append(this.getValue(), castOther.getValue())
            .isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getValue())
            .toHashCode();
    }
}

