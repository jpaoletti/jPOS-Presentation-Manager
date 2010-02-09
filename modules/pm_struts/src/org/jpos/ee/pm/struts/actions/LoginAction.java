/*
 * jPOS Presentation Manager [http://jimport java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.jpos.ee.BLException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.menu.MenuSupport;
import org.jpos.ee.pm.security.SECException;
import org.jpos.ee.pm.security.SECUser;
import org.jpos.ee.pm.security.UserManager;
import org.jpos.util.NameRegistrar.NotFoundException;
  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.struts.actions;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.jpos.ee.BLException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.menu.MenuSupport;
import org.jpos.ee.pm.security.SECException;
import org.jpos.ee.pm.security.SECUser;
import org.jpos.ee.pm.security.UserManager;
import org.jpos.util.NameRegistrar.NotFoundException;

public class LoginAction extends EntityActionSupport {
	private LoginActionForm f;

    /** Opens an hibernate transaction before doExecute*/
	protected boolean openTransaction() { return false;	}
	/**Makes the operation generate an audithory entry*/
	protected boolean isAudited() {	return false; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){ 	return false;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return false; }

    protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
    	if(getPMService().isLoginRequired()){
    		return super.preExecute(rc);
    	}else{
    		return null;
    	}
    }
	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		 int accessCount;

		 if(getPMService().isLoginRequired()){
	        if (rc.getSession().getAttribute(ACCESS_COUNT) == null  )  {
	        	rc.getSession().setAttribute(USER, null);
	        	rc.getSession().setAttribute(MENU, null);
	            accessCount = 1;        
	        }  else {
	            accessCount = (Integer)rc.getSession().getAttribute(ACCESS_COUNT);
	        }
	        f = (LoginActionForm) rc.getForm();
	        
	         
	         UserManager mgr = new UserManager (rc.getDB());
	         try {
	             // This is only used to return an user object to evaluate 
	             // if the user is locked out.
	             SECUser u = mgr.chechUserExistance(f.getUsername());
	             mgr.checkUserLock(u);
	             u = authenticate(rc,accessCount, mgr);
	
	             debug("Login OK. User "+u);
	             loadMenu(rc, u);
	             //PMEntitySupport.getInstance().setDb(rc.getDB());
	             
	             if (u.isChangePassword()) {
	             	return rc.getMapping().findForward("changepassword");		
	             }
	             
	             if(checkPasswordAge(u)) return rc.getMapping().findForward("changepassword");
	                             		
	             //response.sendRedirect (originalUri);
	             return rc.successful();
	         } catch (SECException e) {
	        	PMLogger.error(e);
	         	accessCount++;
	            rc.getSession().setAttribute(ACCESS_COUNT, new Integer(accessCount));
	            rc.getErrors().add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getKey()));
	            try {Thread.sleep (1000); } catch (InterruptedException ee) { } // security delay
	            return rc.fail();
	         }
	     }else{
	    	 SECUser u = new SECUser();
	    	 u.setName(" ");
	    	 loadMenu(rc, u);
	    	 return rc.successful();
	     }
    }
	private void loadMenu(RequestContainer rc, SECUser u)
			throws FileNotFoundException, NotFoundException {
		Menu menu = MenuSupport.getMenu(u,getPMService());
		 rc.getSession().setAttribute(USER, u);
		 rc.getSession().setAttribute(MENU, menu);
	}
	/**
	 * @param accessCount
	 * @param seed
	 * @param cfgMgr
	 * @param mgr
	 * @return The user
	 * @throws BLException
	 */
	private SECUser authenticate(RequestContainer rc, int accessCount, UserManager mgr) throws SECException {
		SECUser u = null;
		//try {
        	String seed = rc.getSession().getId(); 
		    u = mgr.getUserByNick (f.getUsername(), seed, f.getPassword());
		/*} catch (InvalidPasswordException e) {
			u = mgr.getUserByNick (f.getUsername());     
		}*/
		mgr.verifyUser(u);
		return u;
	}

	/**
	 * @param cfgMgr
	 * @param u
	 * @param originalUri
	 * @return
	 * @throws ParseException
	 */
	private boolean checkPasswordAge(SECUser u) throws ParseException {
		/*if (cfgMgr.hasProperty("PasswordAge")) {
		    
		    int passAge = cfgMgr.getInt("PasswordAge");
		    if (passAge != 0) {
		        
		        Calendar cal = Calendar.getInstance();          
		        Calendar calPassExpired = Calendar.getInstance();              
		        DateFormat df = DateFormat.getDateTimeInstance();                                                                                               

		        if (u.hasProperty("PasswordChanged")) {
		        
		            calPassExpired.setTime (df.parse(u.get("PasswordChanged")));                           
		            calPassExpired.add(Calendar.DAY_OF_WEEK, passAge);         
		                                        
		            if (cal.after(calPassExpired)) {
		                    return true;		   
		            }
		        }                        
		    }                                        
		}*/
		return false;
	}


	
}
