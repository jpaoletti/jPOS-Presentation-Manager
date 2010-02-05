package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jpos.ee.Constants;

/**Just a container for a list of operations and some helpers.
 * @author yero jeronimo.paoletti@gmail.com
 * */
public class Operations  extends PMCoreObject implements Constants{
	/**The operation list*/
	private List<Operation> operations;
	/**Optimization*/
	private Map<String,Operations > opsmap;

	/**Returns the operation of the given id or a new default operation
	 * @param id The id
	 * @return The operation*/
	public Operation getOperation(String id){
		for (Iterator<Operation> it = operations.iterator(); it.hasNext();) {
			Operation oper = it.next();
			if(oper.getId().compareTo(id) == 0) return oper;
		}
		return newDefaultOperation(id);
	}
	
	/**A new Operation with the given id
	 * @param id The operation id
	 * @return The new operation*/
	private Operation newDefaultOperation(String id) {
		Operation op = new Operation();
		op.setId(id);
		op.setEnabled(true);
		return op;
	}

	/**Returns the Operations for a given operation. That is the operations that are different to
	 * the given one, enabled and visible in it.
	 * @param operation The operation
	 * @return The operations*/
	public Operations getOperationsFor(Operation operation) {
		if(opsmap == null) opsmap = new HashMap<String, Operations>();
		Operations result = opsmap.get(operation.getId());
		if(result!=null) return result;
		
		result = new Operations();
		List<Operation> r = new ArrayList<Operation>(); 
		for (Operation op : getOperations()) {
			if(op.isVisibleIn(operation.getId()) && op.isEnabled() && !op.equals(operation))
				r.add(op);
		}
		result.setOperations(r);
		opsmap.put(operation.getId(), result);
		return result;
	}

	/**Returns an Operations object for the given scope
	 * @param scope The scope
	 * @return The Operations
	 *  */
	public Operations  getOperationsForScope(String scope) {
		Operations result = new Operations();
		List<Operation> r = new ArrayList<Operation>(); 
		for (Operation op : getOperations()) {
			if(op.getScope()!= null && op.getScope().trim().compareTo(scope) == 0) r.add(op);
		}
		result.setOperations(r);
		return result;
	}

	/**
	 * @return the operations
	 */
	public List<Operation> getOperations() {
		return operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	/**Set also operations service
	 * @see org.jpos.ee.pm.core.PMCoreObject#setService(org.jpos.ee.pm.core.PMService)
	 */
	public void setService(PMService service) {
		if(getOperations() != null)
			for(Operation o : getOperations())
				o.setService(service);
	}
}