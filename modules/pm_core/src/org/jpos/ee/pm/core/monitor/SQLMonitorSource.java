package org.jpos.ee.pm.core.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.jpos.ee.DB;

public class SQLMonitorSource extends MonitorSource {
	private String query;
	private String lastLineQuery;
	private Integer idColumn;

	public void init() {
		setQuery(getConfig("query"));
		setLastLineQuery(getConfig("last-line-query"));
		setIdColumn(Integer.parseInt(getConfig("id-column", "0")));
	}

	public List<MonitorLine> getLinesFrom(Object actual) throws Exception {
		List<MonitorLine> result = new ArrayList<MonitorLine>(); 
		DB db = new DB();
		db.open();
		try {
			String sql = getQuery().trim();
			sql = sql.replaceAll("$actual", (actual==null)?"":actual.toString());
			SQLQuery c = db.session().createSQLQuery(sql);
			List<?> l = c.list();
			for (Iterator<?> iterator = l.iterator(); iterator.hasNext();) {
				Object item = iterator.next();
				MonitorLine line = new MonitorLine();
				
				if (item instanceof Object[]) {
					Object[] objects = (Object[]) item;
					line.setId(objects[getIdColumn()]);
					line.setValue(objects);
				}else{
					line.setId(item);
					Object[] objects = {item};
					line.setValue(objects);
				}
				
            	result.add(line);
			}
		} finally{
			db.close();
		}
		return result;
	}

	public MonitorLine getLastLine() throws Exception {
		MonitorLine result = new MonitorLine();
		DB db = new DB();
		db.open();
		try {
			SQLQuery c = db.session().createSQLQuery(getLastLineQuery().trim());
			c.setMaxResults(1);
			Object item = c.uniqueResult();
			if (item instanceof Object[]) {
				Object[] objects = (Object[]) item;
				result.setId(objects[getIdColumn()]);
				result.setValue(objects);
			}else{
				result.setId(item);
				Object[] objects = {item};
				result.setValue(objects);
			}
		} finally{
			db.close();
		}
		return result;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param lastLineQuery the lastLineQuery to set
	 */
	public void setLastLineQuery(String lastLineQuery) {
		this.lastLineQuery = lastLineQuery;
	}

	/**
	 * @return the lastLineQuery
	 */
	public String getLastLineQuery() {
		return lastLineQuery;
	}

	/**
	 * @param idColumn the idColumn to set
	 */
	public void setIdColumn(Integer idColumn) {
		this.idColumn = idColumn;
	}

	/**
	 * @return the idColumn
	 */
	public Integer getIdColumn() {
		return idColumn;
	}
}