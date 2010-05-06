package org.jpos.ee.pm.security.rules;

import org.jpos.ee.pm.security.SECException;

public class SECRuleMinLength implements SECRuleValidator {

    public void validate(String aplied, String param) throws SECException {
        Integer x = Integer.parseInt(param);
        if(aplied.length() < x) 
            throw new SECException("pm_security.rule.min.length");
    }

}
