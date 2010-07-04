package org.jpos.ee.pm.struts;

import org.jpos.ee.pm.core.EntityContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.security.core.PMSecurityUser;

/**An extension of the org.jpos.ee.pm.core.PMContext class with some helpers
 * for PMStruts.*/
public class PMStrutsContext extends PMContext {
    /**
     * @return the mapping
     */
    public ActionMapping getMapping() {
        return (ActionMapping)get(PM_MAPPINGS);
    }
    /**
     * @param mapping the mapping to set
     */
    public void setMapping(ActionMapping mapping) {
        put(PM_MAPPINGS, mapping);
    }
    /**
     * @return the form
     */
    public ActionForm getForm() {
        return (ActionForm) get(PM_ACTION_FORM);
    }
    /**
     * @param form the form to set
     */
    public void setForm(ActionForm form) {
        put(PM_ACTION_FORM, form);
    }
    
    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return (HttpServletRequest)get(PM_HTTP_REQUEST);
    }
    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        put(PM_HTTP_REQUEST, request);
    }

    /**Getter for the logged user*/
    public PMSecurityUser getUser(){
        PMSecurityUser user = (PMSecurityUser) get(USER);
        return user;
    }

    /**True if there is a user online*/
    public boolean isUserOnLine() {
        return (getUser() != null);
    }
    
    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) get(PM_HTTP_RESPONSE);
    }
    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        put(PM_HTTP_RESPONSE,response);
    }
    
    /* ActionForwards Helpers */
    public ActionForward successful() {
        return getMapping().findForward(SUCCESS);
    }
    
    public ActionForward go() {
        return getMapping().findForward(CONTINUE);
    }

    public ActionForward deny() {
        return getMapping().findForward(DENIED);
    }
    public String getParameter(String s) {
        return getRequest().getParameter(s);
    }
    public HttpSession getSession(){
        return getRequest().getSession();
    }
    
    public PMEntitySupport getEntitySupport(){        
        PMEntitySupport r = (PMEntitySupport)getRequest().getSession().getAttribute(ENTITY_SUPPORT);
        return r;    
    }        
    
    public EntityContainer getEntityContainer(String id) throws PMException{
        EntityContainer ec = (EntityContainer) getSession().getAttribute(id);
        if(ec == null){
            getErrors().add(new PMMessage(ActionMessages.GLOBAL_MESSAGE, "pm_core.entity.not.found", id));
            throw new PMException();
        }
        return ec;
    }
    
    private String getPmId() {
        return (String) getRequest().getAttribute(PM_ID);
    }
    
}
