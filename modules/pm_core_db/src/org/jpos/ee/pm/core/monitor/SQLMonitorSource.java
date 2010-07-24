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