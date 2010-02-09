/*
 * jPOS Presentation Manager [http://import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForward;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMListSupport;
import org.jpos.util.NameRegistrar.NotFoundException;
 WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.struts.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForward;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMList;
import org.jpos.ee.pm.struts.PMListSupport;
import org.jpos.util.NameRegistrar.NotFoundException;

public class FilterAction extends FieldProcessingActionSupport {
	protected ActionForward preExecute(RequestContainer rc) throws NotFoundException {
		ActionForward r = super.preExecute(rc);
		if(r != null) return r;
		if(rc.getParameter(FINISH)==null){
			if(rc.getEntity_container().getFilter()==null){
				//Creates filter bean and put it in session
				//Object filter;
				try {
					debug("Creating Filter Bean");
					//filter = getPMService().getFactory().newInstance (rc.getEntity().getClazz());
					EntityFilter filter = new EntityFilter();
					EntityInstanceWrapper wrapper = new EntityInstanceWrapper();
					int top = Integer.parseInt( rc.getOperation().getConfig("instances", "1") );
					for(int i = 0 ; i < top; i++){
						Object n1 = getPMService().getFactory().newInstance (rc.getEntity().getClazz());
						wrapper.add(n1);
					}
					filter.setInstance(wrapper);
					rc.getEntity_container().setFilter(filter);
				} catch (ConfigurationException e) {
					PMLogger.error(e);
					rc.getErrorlist().put(ENTITY, e.getMessage());
					return rc.fail();
				}
			}
			return rc.go();
		}else{
			rc.getEntity_container().getFilter().getFilters().clear();
			for (Field f : rc.getEntity().getFields()) {
				proccessField(rc, f, rc.getEntity_container().getFilter().getInstance());
	        }
			rc.getEntity_container().getFilter().process(rc.getEntity());
		}
		
		return r;
	}
	
	protected ActionForward doExecute(RequestContainer rc) throws Exception {
		//Aca actualizo la lista con el filtro
		PMListSupport pmls = new PMListSupport(rc.getDbs());
		PMList pmlist = rc.getList();
		List<Object> contents = null;
		Integer total = 0;
		if(rc.getEntity().isPersistent()){
			//we must get the list form the DB
			//contents= pmls.getContentList(rc.getEntity(),pmls.buildFilterMap(rc.getEntity().getFields(), rc.getEntity_container().getFilter()), null);
			contents= pmls.getContentList(rc.getEntity(),rc.getEntity_container().getFilter(), pmlist);
	    	total= pmls.getTotal();
	    }else{
			//An empty list that will be filled on an a list context
			contents = new ArrayList<Object>();
		}
		PMList pmList = rc.getList();
		pmList.setContents(contents);
		pmList.setTotal(total);
		return rc.successful();
	}

}