package org.jpos.ee.pm.security.rules;

import java.io.Serializable;
import java.util.Date;

import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.security.SECException;
import org.jpos.ee.pm.security.SECUser;

public class SECRule implements Serializable{
    private static final long serialVersionUID = -8540189223203043891L;
    private long id;
    private int applyTo; //0 username ; 1 password
    private String description;
    private String parameter;
    private String validatorClass;
    private boolean enabled;
    private SECUser updateUser;
    private Date updateDate;
    
    public void validate(String username, String password) throws SECException{
        if(!isEnabled()) return;
        System.out.println("Validating: "+getValidatorClass());
        SECRuleValidator srv = (SECRuleValidator) EntitySupport.newObjectOf(getValidatorClass());
        if(getApplyTo() == 0)
            srv.validate(username, getParameter());
        else
            srv.validate(password, getParameter());
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    public String getParameter() {
        return parameter;
    }
    public void setApplyTo(Integer applyTo) {
        this.applyTo = applyTo;
    }
    public Integer getApplyTo() {
        return applyTo;
    }
    public void setValidatorClass(String validatorClass) {
        this.validatorClass = validatorClass;
    }
    public String getValidatorClass() {
        return validatorClass;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setUpdateUser(SECUser updateUser) {
        this.updateUser = updateUser;
    }

    public SECUser getUpdateUser() {
        return updateUser;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

}
