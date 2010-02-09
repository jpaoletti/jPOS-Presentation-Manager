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
package org.jpos.ee.pm.core;

import java.util.Map;

import org.jpos.ee.DB;

/**This class provides a way to add read only fields to any operation of an entity. A programmer can subclass 
 * this class to add visualization functionality such as balances, counters or any complex or simple function
 * that requires more than a getter.<br/>
 * 
 * Not yet implemented
 *    
 * @author yero jeronimo.paoletti@gmail.com
 *  
 * */
public abstract class VisualizationWrapper {
	/** A database connection to get any information*/
	private DB db;
	
	/** A map with the calculated values of the field*/	
	private Map<String,Object> values;

	/**This abstract method must be defined for each implementation and must
	 * fill the values map.
	 * @param object The entity instance to get any data required for calculation*/
	public abstract void load(Object object);
	
	/**This function is automatically invoked to get the values configured in the entity 
	 * configuration file
	 * 
	 * @param name The name of the requested value 
	 * @return The value for the given field name
	 * */
	public Object get(String name){
		return values.get(name);
	}
	
	/** Setter for db field. This method is invoked automatically by the PM engine
	 * @param db the db to set
	 */
	public void setDb(DB db) {
		this.db = db;
	}

	/**A getter for the database
	 * @return the db
	 */
	public DB getDb() {
		return db;
	}
}
