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

package org.jpos.ee.pm.validator;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Field;

/**Validate that the field value is a valid name. Avaible properties are:
 * msg: the message to show when there is an invalid character. This should be a key for messages properties file
 * max-length: maximum length of the string
 * max-length-msg: message to show if the name is too long
 * min-length: minimum length of the string
 * min-length-msg:  message to show if the name is too short
 * 
 * @author yero jeronimo.paoletti@gmail.com */
public class IsName extends ValidatorSupport {
	
	/**The validate method*/
	public ValidationResult validate(Entity entity, Field field, Object entityvalue, String fieldvalue) {
		ValidationResult res = new ValidationResult();
		res.setSuccessful(true);
        if (!isName (fieldvalue)){
        	res.setSuccessful(false);
        	res.getMessages().put(field.getId(), get ("msg", "Invalid characters"));
        }
        int len = fieldvalue.length();
        if (len > getInt ("max-length")){
        	res.setSuccessful(false);
        	res.getMessages().put(field.getId(),get ("max-length-msg", "Too long"));
        }
        if (len < getInt ("min-length")){
        	res.setSuccessful(false);
        	res.getMessages().put(field.getId(),get ("min-length-msg", "Too short"));
        }
        return res;
	}
}

