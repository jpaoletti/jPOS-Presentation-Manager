package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.core.PMException;

/**
 * Exception that indicates the engine to forward a diferent mapping than
 * success without failing
 * 
 * @author jpaoletti
 */
public class PMForwardException extends PMException {
    /**
     * Constructor
     * 
     * @param key The mapping key to forward
     */
    public PMForwardException(String key) {
        super(key);
    }

    private static final long serialVersionUID = 8043873501146882128L;
}