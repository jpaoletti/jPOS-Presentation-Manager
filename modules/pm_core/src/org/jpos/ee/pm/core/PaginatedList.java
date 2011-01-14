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
import java.util.List;

import org.jpos.util.DisplacedList;

/**
 * This list represents a list with a paged representation.
 * 
 * @author jpaoletti
 */
public class PaginatedList {

    public static final int DEFAULT_PAGE_SIZE = 10;
    private Entity entity;
    private DisplacedList<Object> contents;
    private Integer page;
    private Integer pages;
    private Long total;
    private Integer rowsPerPage;
    private String order;
    private boolean desc;
    private Operations operations;
    private Operations rowOperations;
    private boolean searchable;
    private boolean paginable;
    private boolean showRowNumber;
    private String operationColWidth;
    private boolean hasSelectedScope;

    /**
     * Returns a list with the existing pages index
     * 
     * @return
     */
    public List<Integer> getPageRange() {
        List<Integer> r = new ArrayList<Integer>();
        for (int i = 1; i <= getPages(); i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * String representation of the list
     * @return
     */
    @Override
    public String toString() {
        return "PMList [entity=" + entity + ", page " + page + " of "
                + pages + ", total=" + total + ", rowsPerPage=" + rowsPerPage
                + ", order=" + order + ", desc=" + desc + "]";
    }

    /**
     * Default constructor
     */
    public PaginatedList() {
        this.page = 1;
        rowsPerPage = 10; //Default
    }

    /**
     * Constructor with contents and total
     * 
     * @param contents
     * @param total
     */
    public PaginatedList(DisplacedList<Object> contents, Long total) {
        super();
        this.contents = contents;
        rowsPerPage = 10; //Default
        this.page = 1;
        if (total != null) {
            setTotal(total);
        }
    }

    /**
     *
     * @param rowsPerPage
     */
    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        setTotal(total);
    }

    /**
     *
     * @return
     */
    public String getOrder() {
        return order;
    }

    /**
     *
     * @param order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     *
     * @return
     */
    public boolean isDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     */
    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     */
    public DisplacedList<Object> getContents() {
        if (contents == null) {
            contents = new DisplacedList<Object>();
        }
        return contents;
    }

    /**
     *
     * @param contents
     */
    public void setContents(DisplacedList<Object> contents) {
        this.contents = contents;
        getContents().setDisplacement((int) ((getPage() - 1) * getRowsPerPage()));
    }

    /**
     *
     * @return
     */
    public Integer getPage() {
        if (page == null) {
            return 1;
        }
        return page;
    }

    /**
     *
     * @param page
     */
    public void setPage(Integer page) {
        //Out of range page sets to last one
        if (getTotal() != null && page > getPages()) {
            this.page = getPages();
        } else {
            this.page = page;
        }
    }

    /**
     *
     * @return
     */
    public Integer getPages() {
        if (pages == null) {
            return 1;
        }
        return pages;
    }

    /**
     *
     * @param pages
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     *
     * @return
     */
    public Long getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public final void setTotal(Long total) {
        this.total = total;
        if (total != null) {
            if (total % rowsPerPage == 0) {
                this.pages = (int) (total / rowsPerPage);
            } else {
                this.pages = (int) (total / rowsPerPage) + 1;
            }
        }
    }

    /**
     *
     * @return
     */
    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    /**
     *
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     *
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     *
     * @param operations
     */
    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    /**
     *
     * @return
     */
    public Operations getOperations() {
        return operations;
    }

    /**
     *
     * @param operations
     */
    public void setRowOperations(Operations operations) {
        rowOperations = operations;
    }

    /**
     *
     * @return
     */
    public Operations getRowOperations() {
        return rowOperations;
    }

    /**
     * Returns the starting index of the list
     * @return
     */
    public Integer from() {
        return (this.getPage() != null) ? (((getPage() - 1) * getRowsPerPage())) : 0;
    }

    /**
     * Return the page size of the list (or de Row Per Page value)
     * @return
     */
    public Integer rpp() {
        return (getRowsPerPage() != null) ? getRowsPerPage() : DEFAULT_PAGE_SIZE;
    }

    /**
     * @param searchable the searchable to set
     */
    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    /**
     * @return the searchable
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * @param paginable the paginable to set
     */
    public void setPaginable(boolean paginable) {
        this.paginable = paginable;
    }

    /**
     * @return the paginable
     */
    public boolean isPaginable() {
        return paginable;
    }

    /**
     * @param showRowNumber the showRowNumber to set
     */
    public void setShowRowNumber(boolean showRowNumber) {
        this.showRowNumber = showRowNumber;
    }

    /**
     * @return the showRowNumber
     */
    public boolean isShowRowNumber() {
        return showRowNumber;
    }

    /**
     * @param operationColWidth the operationColWidth to set
     */
    public void setOperationColWidth(String operationColWidth) {
        this.operationColWidth = operationColWidth;
    }

    /**
     * @return the operationColWidth
     */
    public String getOperationColWidth() {
        return operationColWidth;
    }

    /**
     * @param hasSelectedScope the hasSelectedScope to set
     */
    public void setHasSelectedScope(boolean hasSelectedScope) {
        this.hasSelectedScope = hasSelectedScope;
    }

    /**
     * @return the hasSelectedScope
     */
    public boolean isHasSelectedScope() {
        return hasSelectedScope;
    }

    public Integer getListTotalDigits() {
        try {
            return (getTotal() == null || getTotal() == 0) ? 1 : (int) Math.log10(getTotal()) + 1;
        } catch (Exception ex) {
            return 0;
        }
    }
}
