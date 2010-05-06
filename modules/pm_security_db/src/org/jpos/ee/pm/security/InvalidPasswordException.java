package org.jpos.ee.pm.security;


public class InvalidPasswordException extends SECException {
    private static final long serialVersionUID = 4594969485542612781L;

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String s, String ... detail) {
        super(s, detail);
    }

    public InvalidPasswordException(String s, String detail) {
        super(s, detail);
    }

    public InvalidPasswordException(String s, Throwable nested) {
        super(s, nested);
    }

    public InvalidPasswordException(String s) {
        super(s);
    }

    public InvalidPasswordException(Throwable nested) {
        super(nested);
    }
    
}
