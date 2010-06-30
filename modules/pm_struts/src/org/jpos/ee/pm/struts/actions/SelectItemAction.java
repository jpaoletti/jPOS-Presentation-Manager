package org.jpos.ee.pm.struts.actions;

import java.util.Set;

import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class SelectItemAction extends EntityActionSupport {
	  
	/**Opens an hibernate transaction before doExecute*/
    protected boolean openTransaction() { return false;    }
    /**Makes the operation generate an audithory entry*/
    protected boolean isAudited() {    return false; }
    /**Forces execute to check if any user is logged in*/
    protected boolean checkUser(){     return false;}
    /**Forces execute to check if there is an entity defined in parameters*/
    protected boolean checkEntity(){ return true; }

    protected boolean prepare(PMStrutsContext ctx) throws PMException {
    	/*select all */
    	/*List<Integer> selected = new ArrayList<Integer>();
    	for (int i = 0; i < ctx.getEntityContainer().getList().getTotal()-1; i++) {
			selected.add(i);
		}
    	ctx.getEntityContainer().setSelectedIndexes(selected);*/
    	super.prepare(ctx);
    	Integer idx = Integer.parseInt(ctx.getRequest().getParameter("idx"));
    	Set<Integer> selectedIndexes = ctx.getEntityContainer().getSelectedIndexes();
    	if(selectedIndexes.contains(idx)) {
    		selectedIndexes.remove(idx);
    		ctx.getPresentationManager().debug(this, "Deselected "+idx);
    	}else{
    		selectedIndexes.add(idx);
    		ctx.getPresentationManager().debug(this, "Selected "+idx);
    	}
        return true;
    }
    
    protected void doExecute(PMStrutsContext ctx)throws PMException{}
}
