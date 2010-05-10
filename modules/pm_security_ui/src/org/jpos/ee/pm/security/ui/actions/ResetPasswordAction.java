package org.jpos.ee.pm.security.ui.actions;

import java.util.UUID;

import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.ee.pm.struts.actions.RowActionSupport;

public class ResetPasswordAction extends RowActionSupport {

    protected boolean openTransaction() { return true;    }
    protected boolean checkUser(){     return true;}
    protected boolean checkEntity(){ return true; }
    public boolean testSelectedExist(){ return true; }

	protected void doExecute(PMStrutsContext ctx) throws PMException {
        PMSecurityUser u = (PMSecurityUser) ctx.getSelected().getInstance();
        try {
        	if(ctx.getUser().equals(u))
        		throw new PMException("pm.user.cant.reset.his.psw");
        	String generatedpsw = UUID.randomUUID().toString().substring(0, 8);
            getConnector(ctx).changePassword(u.getUsername(), null, generatedpsw );
            ctx.getRequest().setAttribute("generatedpsw", generatedpsw);
            ctx.getRequest().setAttribute("username", u.getUsername());
        } catch (PMSecurityException e) {
            
        }
        
    }
    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

}
