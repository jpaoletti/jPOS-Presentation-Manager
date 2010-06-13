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
package org.jpos.ee.pm.struts.actions;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class ListActionForm extends ActionForm {
    private static final long serialVersionUID = -7853709292363297456L;
    private Integer page;
    private String ftype;
    private List<String> ffields;
    private List<String> fvalues;
    private Integer rowsPerPage;
    private String order;
    private boolean desc;
    
    public ListActionForm() {
        super();
    }
    
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public boolean isDesc() {
        return desc;
    }
    public void setDesc(boolean desc) {
        this.desc = desc;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getRowsPerPage() {
        return rowsPerPage;
    }
    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public String getFtype() {
        return ftype;
    }
    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
    public List<String> getFfields() {
        return ffields;
    }
    public void setFfields(List<String> ffields) {
        this.ffields = ffields;
    }
    public List<String> getFvalues() {
        return fvalues;
    }
    public void setFvalues(List<String> fvalues) {
        this.fvalues = fvalues;
    }
}
