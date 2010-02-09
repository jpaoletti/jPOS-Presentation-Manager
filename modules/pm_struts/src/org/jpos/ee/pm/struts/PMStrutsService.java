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
 */
package org.jpos.ee.pm.struts;

/**This is an implementation of the PMService for struts.*/
import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.PMService;

public class PMStrutsService extends PMService implements Constants{
    
	/**Visualization wrapper. If there is no converter then I use this "void".
	 * If the string is a struts url (jsp or do) the I return it as is. Otherwise
	 * I asume that the text must be shown in void converter as a plain text.
	 * */
    public String visualizationWrapper(String s){
    	if(s==null)return "void.jsp?text=";
    	if(s.contains(".jsp?") || s.contains(".do?"))
    		return s;
    	else
    		return "void.jsp?text="+s;
    }

    /**Create and fill a new Entity Container */
	public EntityContainer newEntityContainer(String sid){
    	Entity e = lookupEntity(sid);
    	if(e == null) return null;
		return new EntityContainer(e, HASH);
    }
    
	/**Looks for an Entity with the given id*/
    private Entity lookupEntity(String sid) {
		for(Integer i = 0 ; i < getEntities().size() ; i++){
			Entity e = getEntities().get(i);
			if(e!=null && sid.compareTo(EntityContainer.buildId(HASH, e.getId())) == 0){
				return getEntity(e.getId());
			}
		}
		return null;
	}
    
}