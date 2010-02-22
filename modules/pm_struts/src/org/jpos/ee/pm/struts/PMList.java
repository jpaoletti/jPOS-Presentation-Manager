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

import java.util.ArrayList;
import java.util.List;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Operations;

public class PMList implements Constants{
	private Entity entity;
	private List<Object> contents;
	private Integer page;
	private Long pages;
	private Long total;
	private Integer rowsPerPage;
	private String order;
	private boolean desc;
	private Operations operations;
	private Operations rowOperations;
	
	public List<Integer> getPageRange(){
		List<Integer> r = new ArrayList<Integer>();
		for(int i = 1 ; i <= getPages() ; i++) r.add(i);
		return r;
	}
	public PMList(){
		this.page = 1;
		rowsPerPage = 10; //Default
	}
	
	public PMList(List<Object> contents, Long total) {
		super();
		this.contents = contents;
		rowsPerPage = 10; //Default
		this.page = 1;
		setTotal(total); 
	}
	
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		//this.page = 1;
		this.pages = new Long(total.longValue() / rowsPerPage + 1);
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

	public List<Object> getContents() {
		if(contents==null) contents = new ArrayList<Object>();
		return contents;
	}
	public void setContents(List<Object> contents) {
		this.contents = contents;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Long getPages() {
		return pages;
	}
	public void setPages(Long pages) {
		this.pages = pages;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
		this.pages = total / rowsPerPage;
	}
	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setOperations(Operations operations) {
		this.operations = operations;
	}

	public Operations getOperations() {
		return operations;
	}

	public void setRowOperations(Operations operations) {
		rowOperations = operations;
	}

	public Operations getRowOperations() {
		return rowOperations;
	}
	
	public Integer from(){
		return (this.getPage()!=null)?(((getPage()-1) * getRowsPerPage())):0;
	}
	
	public Integer rpp(){
		return (getRowsPerPage()!=null)?getRowsPerPage():DEFAULT_PAGE_SIZE;
	}
}
