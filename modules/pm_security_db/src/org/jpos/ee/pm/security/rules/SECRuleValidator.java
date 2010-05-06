package org.jpos.ee.pm.security.rules;

import org.jpos.ee.pm.security.SECException;

public interface SECRuleValidator {
    public void validate(String aplied, String param)throws SECException;
}
