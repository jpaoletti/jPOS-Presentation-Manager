/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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
import java.util.HashMap;
import java.util.Map;


/**The result of a validation.
 * @author yero jeronimo.paoletti@gmail.com 
 */
public class ValidationResult {
	/**True when the validation was successful*/
	private boolean successful;
	/**Error messages. The key is the field id and the value is the error message*/
	private Map<String,String> messages;
	
	/**Default constructor*/
	public ValidationResult() {
		super();
		setMessages(new HashMap<String, String>());
	}

	/**
	 * @param successful the successful to set
	 */
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	/**
	 * @return the successful
	 */
	public boolean isSuccessful() {
		return successful;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Map<String,String> messages) {
		this.messages = messages;
	}

	/**
	 * @return the messages
	 */
	public Map<String,String> getMessages() {
		return messages;
	}	
}