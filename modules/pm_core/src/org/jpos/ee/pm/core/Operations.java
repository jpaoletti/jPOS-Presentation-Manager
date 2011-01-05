/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**Just a container for a list of operations and some helpers.
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * */
public class Operations extends PMCoreObject {

    /**The operation list*/
    private List<Operation> operations;
    /**Optimization*/
    private Map<String, Operations> opsmap;

    /**Returns the operation of the given id or a new default operation
     * @param id The id
     * @return The operation*/
    public Operation getOperation(String id) {
        for (Iterator<Operation> it = operations.iterator(); it.hasNext();) {
            Operation oper = it.next();
            if (oper.getId().compareTo(id) == 0) {
                return oper;
            }
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
        if (opsmap == null) {
            opsmap = new HashMap<String, Operations>();
        }
        Operations result = opsmap.get(operation.getId());
        if (result != null) {
            return result;
        }

        result = new Operations();
        List<Operation> r = new ArrayList<Operation>();
        for (Operation op : getOperations()) {
            if (op.isDisplayed(operation.getId()) && op.isEnabled() && !op.equals(operation)) {
                r.add(op);
            }
        }
        result.setOperations(r);
        opsmap.put(operation.getId(), result);
        return result;
    }

    public Operations getItemOperations() {
        return getOperationsForScope(SCOPE_ITEM);
    }

    public Operations getGeneralOperations() {
        return getOperationsForScope(SCOPE_GRAL);
    }

    /**Returns an Operations object for the given scope
     * @param scopes The scopes
     * @return The Operations
     *  */
    public Operations getOperationsForScope(String... scopes) {
        Operations result = new Operations();
        List<Operation> r = new ArrayList<Operation>();
        for (Operation op : getOperations()) {
            if (op.getScope() != null) {
                String s = op.getScope().trim();
                for (int i = 0; i < scopes.length; i++) {
                    String scope = scopes[i];
                    if (s.compareTo(scope) == 0) {
                        r.add(op);
                        break;
                    }
                }
            }

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

    /**
     * Returns the number of operations in the list
     * 
     * @return
     */
    public int count() {
        return getOperations().size();
    }
}
