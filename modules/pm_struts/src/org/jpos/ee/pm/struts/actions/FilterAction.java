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

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMList;

public class FilterAction extends FieldProcessingActionSupport {
	protected boolean prepare(PMContext ctx) throws PMException {
		super.prepare(ctx);
		if(ctx.getParameter(FINISH)==null){
			if(ctx.getEntityContainer().getFilter()==null){
				//Creates filter bean and put it in session
				//Object filter;
				try {
					//filter = getPMService().getFactory().newInstance (rc.getEntity().getClazz());
					EntityFilter filter = new EntityFilter();
					EntityInstanceWrapper wrapper = new EntityInstanceWrapper();
					int top = Integer.parseInt( ctx.getOperation().getConfig("instances", "1") );
					for(int i = 0 ; i < top; i++){
						Object n1 = getPMService().getFactory().newInstance (ctx.getEntity().getClazz());
						wrapper.add(n1);
					}
					filter.setInstance(wrapper);
					ctx.getEntityContainer().setFilter(filter);
				} catch (ConfigurationException e) {
					PMLogger.error(e);
					ctx.getErrors().add(new PMMessage(ENTITY, e.getMessage()));
					throw new PMException();
				}
			}
			throw new PMForwardException(CONTINUE);
		}else{
			ctx.getEntityContainer().getFilter().getFilters().clear();
			for (Field f : ctx.getEntity().getFields()) {
				proccessField(ctx, f, ctx.getEntityContainer().getFilter().getInstance());
	        }
			ctx.getEntityContainer().getFilter().process(ctx.getEntity());
			return true;
		}
	}
	
	protected void doExecute(PMContext ctx) throws PMException {
		//PMListSupport pmls = new PMListSupport();
		PMList pmlist = ctx.getList();
		List<Object> contents = null;
		Long total = new Long(0);
		if(ctx.getEntity().isPersistent()){
			ctx.put(PM_LIST_ORDER, pmlist.getOrder());
			ctx.put(PM_LIST_ASC, !pmlist.isDesc());
			contents = (List<Object>) ctx.getEntity().getList(ctx , ctx.getEntityContainer().getFilter(), pmlist.from(), pmlist.rpp());
			total = ctx.getEntity().getDataAccess().count(ctx);
		}else{
			//An empty list that will be filled on an a list context
			contents = new ArrayList<Object>();
		}
		PMList pmList = ctx.getList();
		pmList.setContents(contents);
		pmList.setTotal(total);
	}

}