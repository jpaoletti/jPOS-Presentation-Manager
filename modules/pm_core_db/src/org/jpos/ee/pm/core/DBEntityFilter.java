/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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
 */package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class DBEntityFilter extends EntityFilter {
    private List<Criterion> filters;
    
    public void clear() {
        getFilters().clear();
    }

    public void process(Entity entity){
        debug("Processing filter");
        for(Field field : entity.getAllFields()){
            //This is not the best way but works for now
            if(field.shouldDisplay("filter")){
                List<Object> values = new ArrayList<Object>();
                for(Object o : getInstance().getInstances()){
                    values.add(EntitySupport.get(o, field.getId()));
                }
                debug("VALUES ["+field.getId()+"]: "+values);
                Criterion c =null;
                //if(false){
                    /*String co = rc.getParameter("compare_operation");
                    Criterion c =null;
                       if(co!=null && co.compareTo("")!=0){
                           c = getCompareCriterion(co, field.getId(), converted);
                       }else{*/
                if(values.get(0)!=null){
                    if(values.get(0) instanceof String)
                           c = Restrictions.ilike(field.getId(), "%"+values.get(0)+"%");
                       else
                           c = Restrictions.eq(field.getId(), values.get(0));
                       if(c!=null) getFilters().add(c);
                }
            }
        }
        debug("FILTERS: "+getFilters());
    }
    
    protected Criterion getCompareCriterion(String co, String fid, Object converted) {
        // TODO Make this better
        
        if(co.compareTo(__LT__)==0)
            return Restrictions.lt(fid, converted);
        if(co.compareTo(__LE__)==0)
            return Restrictions.le(fid, converted);
        if(co.compareTo(__GT__)==0)
            return Restrictions.gt(fid, converted);
        if(co.compareTo(__GE__)==0)
            return Restrictions.ge(fid, converted);
        if(co.compareTo(__NE__)==0)
            return Restrictions.ne(fid, converted);
    
        if(co.compareTo(__BETWEEN__)==0){
            Entry<Object, Object> entry = (Entry<Object, Object>)converted;
            Object lo = entry.getKey();
            Object hi = entry.getValue();
            return Restrictions.between(fid, lo, hi);
        }
        return Restrictions.eq(fid, converted);
    }    
    
    public DBEntityFilter(){
        this.setFilters(new ArrayList<Criterion>());
    }

    public void setFilters(List<Criterion> filters) {
        this.filters = filters;
    }

    public List<Criterion> getFilters() {
        return filters;
    }
}