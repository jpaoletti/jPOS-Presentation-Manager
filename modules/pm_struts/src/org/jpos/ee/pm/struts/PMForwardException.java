package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.core.PMException;

public class PMForwardException extends PMException {
	public PMForwardException(String key) {
		super(key);
	}

	private static final long serialVersionUID = 8043873501146882128L;
}