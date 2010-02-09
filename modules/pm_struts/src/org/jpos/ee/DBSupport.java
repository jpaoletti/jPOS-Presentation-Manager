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
package org.jpos.ee;

/**A Helper class for DB access
 * @author jpaoletti
 * */
import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jpos.ee.pm.core.PMLogger;

public class DBSupport {

	private DB db;
	
	public DBSupport(DB db) {
		this.db = db;
	}
	
	public Object get(String clazz, Serializable id)throws ClassNotFoundException{
		Object r = db.session().get(Class.forName (clazz), id);
	    return r;
	}

	protected DB getDb() {
		return db;
	}

	protected void setDb(DB db) {
		this.db = db;
	}
	
	public Session session(){
		return getDb().session(); 
	}

	public void delete(Object object) throws HibernateException{
		Transaction tx  = session().beginTransaction();
		try {
			session().delete(object);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			throw e;
		}
	}
	
	public static DB getNewDB(){
		try {
			DB db = new DB();
			db.open();
			return db;
		} catch (Exception e) {
			PMLogger.error(e);
			return null;
		}
	}	
}