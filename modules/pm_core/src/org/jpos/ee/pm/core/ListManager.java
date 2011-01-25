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

import java.util.List;
import org.jpos.ee.Constants;
import org.jpos.util.DisplacedList;

/**
 *
 * @author jpaoletti
 */
public class ListManager {

    public PaginatedList initList(PMContext ctx, Operations operations) throws PMException {
        PaginatedList pmlist = new PaginatedList();
        //Initial values
        pmlist.setDesc(false);
        pmlist.setOrder(null);
        pmlist.setPage(1);
        pmlist.setRowsPerPage(10);

        pmlist.setEntity(ctx.getEntity());
        pmlist.setOperations(operations.getOperationsForScope(Constants.SCOPE_GRAL, Constants.SCOPE_SELECTED));
        String sortfield = ctx.getOperation().getConfig("sort-field");
        String sortdirection = ctx.getOperation().getConfig("sort-direction");
        if (sortfield != null) {
            pmlist.setOrder(sortfield);
            if (sortdirection != null && sortdirection.toLowerCase().compareTo("desc") == 0) {
                pmlist.setDesc(true);
            }
        }
        return pmlist;
    }

    public void configureList(final PMContext ctx, final PaginatedList pmlist, Operations operations) throws PMException {
        List<Object> contents = null;
        Long total = null;

        try {
            if (isPaginable(ctx)) {
                contents = (List<Object>) ctx.getEntity().getList(ctx, ctx.getEntityContainer().getFilter(), pmlist.from(), pmlist.rpp());
            } else {
                contents = (List<Object>) ctx.getEntity().getList(ctx, ctx.getEntityContainer().getFilter(), null, null);
            }
            if (!ctx.getEntity().getNoCount()) {
                total = ctx.getEntity().getDataAccess().count(ctx);
            }
        } catch (Exception e) {
            ctx.getPresentationManager().error(e);
            throw new PMException("pm.operation.cant.load.list");
        }
        ctx.getPresentationManager().debug(this, "List Contents: " + contents);
        ctx.getEntityContainer().setList(pmlist);
        pmlist.setContents(new DisplacedList<Object>(contents));
        pmlist.setTotal(total);
        ctx.getPresentationManager().debug(this, "Resulting list: " + pmlist);
        pmlist.setRowsPerPage(pmlist.rpp());
        prepareParameters(ctx, operations);
    }

    public boolean isPaginable(PMContext ctx) {
        return ctx.getOperation().getConfig("paginable", "true").compareTo("true") == 0;
    }

    private void prepareParameters(PMContext ctx, Operations operations) throws PMException {
        boolean searchable = ctx.getOperation().getConfig("searchable", "true").compareTo("true") == 0;
        Boolean showRowNumber = ctx.getOperation().getConfig("show-row-number", "false").compareTo("true") == 0;
        String operationColWidth = ctx.getOperation().getConfig("operation-column-width", "50px");

        ctx.getList().setSearchable(searchable);
        ctx.getList().setPaginable(isPaginable(ctx));
        ctx.getList().setShowRowNumber(showRowNumber);
        ctx.getList().setOperationColWidth(operationColWidth);
        ctx.getList().setHasSelectedScope(operations.getOperationsForScope(Constants.SCOPE_SELECTED).count() > 0);
    }
}
