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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityFilter extends PMCoreObject {

    private Map<String, FilterOperation> filterOperations;
    private Map<String, List<Object>> filterValues;

    /**
     * Default constructor
     */
    public EntityFilter() {
        filterOperations = new HashMap<String, FilterOperation>();
        filterValues = new HashMap<String, List<Object>>();
    }

    public boolean isOperation(String fid, FilterOperation oper) {
        return getFilterOperations().get(fid) == oper;
    }

    public void process(Entity entity) {
    }

    public void clear() {
    }

    public void addFilter(String fieldId, List<Object> values, FilterOperation operation){
        filterOperations.put(fieldId, operation);
        filterValues.put(fieldId, values);
    }

    public Map<String, FilterOperation> getFilterOperations() {
        return filterOperations;
    }

    public Map<String, List<Object>> getFilterValues() {
        return filterValues;
    }
    

    public void setFilterOperations(Map<String, FilterOperation> filterOperations) {
        this.filterOperations = filterOperations;
    }

    public FilterOperation getFilterOperation(String id) {
        final FilterOperation result = getFilterOperations().get(id);
        if (result != null) {
            return result;
        } else {
            return FilterOperation.EQ;
        }
    }
}
