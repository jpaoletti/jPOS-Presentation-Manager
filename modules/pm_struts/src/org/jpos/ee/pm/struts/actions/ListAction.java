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


import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.operations.ListOperation;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class ListAction extends ActionSupport {

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        final ListActionForm f = (ListActionForm) ctx.getForm();
        if (ctx.getParameter(FINISH) != null) {
            ctx.put("order",f.getOrder());
            ctx.put("desc",f.isDesc());
            ctx.put("page",((f.getPage() != null && f.getPage() > 0) ? f.getPage() : 1));
            ctx.put("rows_per_page",f.getRowsPerPage());
        }else{
            ctx.put("order",null);
            ctx.put("desc",false);
            ctx.put("page",1);
            ctx.put("rows_per_page",10);
        }
        ListOperation op = new ListOperation("list");
        op.excecute(ctx);

        /*final ListManager listManager = new ListManager();
        final Operations operations = (Operations) ctx.getSession().getAttribute(OPERATIONS);

        PaginatedList pmlist = ctx.getList();
        if (pmlist == null) {
        pmlist = listManager.initList(ctx, operations);
        }

        if (ctx.getParameter(FINISH) != null) {
        pmlist.setOrder(f.getOrder());
        pmlist.setDesc(f.isDesc());
        pmlist.setPage((f.getPage() != null && f.getPage() > 0) ? f.getPage() : 1);
        pmlist.setRowsPerPage(f.getRowsPerPage());
        }

        ctx.put(PM_LIST_ORDER, pmlist.getOrder());
        ctx.put(PM_LIST_ASC, !pmlist.isDesc());
        listManager.configureList(ctx, pmlist, operations);*/
    }
}
