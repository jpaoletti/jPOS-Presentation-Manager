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