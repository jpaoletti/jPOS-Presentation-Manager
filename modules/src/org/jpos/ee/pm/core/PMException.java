package org.jpos.ee.pm.core;


public class PMException extends Exception {
	private String key;
	private static final long serialVersionUID = -1685585143991954053L;

	public PMException(String key) {
		setKey(key);
	}

	public PMException() {
		super();
	}

	public PMException(Throwable nested) {
		super(nested);
	}

	public PMException(String s, Throwable nested) {
		super(s,nested);
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
