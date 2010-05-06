package org.jpos.ee.pm.security;

import org.jpos.ee.pm.core.PMException;

public class SECException extends PMException {
    private static final long serialVersionUID = 4594969485542612791L;
    private String[] details;

    public SECException() {
        super();
    }

    public SECException(String s, String ... details ) {
        super(s);
        this.setDetails(details); 
    }

    public SECException(String s, Throwable nested) {
        super(s, nested);
    }

    public SECException(String s) {
        super(s);
    }

    public SECException(Throwable nested) {
        super(nested);
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String[] details) {
        this.details = details;
    }

    /**
     * @return the details
     */
    public String[] getDetails() {
        return details;
    }
    

}
