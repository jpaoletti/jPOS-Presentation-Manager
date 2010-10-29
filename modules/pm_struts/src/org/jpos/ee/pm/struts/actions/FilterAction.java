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

import org.jpos.core.ConfigurationException;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.FilterOperation;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.util.DisplacedList;

public class FilterAction extends FieldProcessingActionSupport {

    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        if (ctx.getParameter(FINISH) == null) {
            if (ctx.getEntityContainer().getFilter() == null) {
                //Creates filter bean and put it in session
                //Object filter;
                try {
                    //filter = getPMService().getFactory().newInstance (rc.getEntity().getClazz());
                    EntityFilter filter = ctx.getEntity().getDataAccess().createFilter(ctx);
                    EntityInstanceWrapper wrapper = new EntityInstanceWrapper();
                    int top = Integer.parseInt(ctx.getOperation().getConfig("instances", "1"));
                    for (int i = 0; i < top; i++) {
                        Object n1 = getPMService().getFactory().newInstance(ctx.getEntity().getClazz());
                        wrapper.add(n1);
                    }
                    filter.setInstance(wrapper);
                    ctx.getEntityContainer().setFilter(filter);
                } catch (ConfigurationException e) {
                    ctx.getPresentationManager().error(e);
                    ctx.getErrors().add(new PMMessage(ENTITY, e.getMessage()));
                    throw new PMException();
                }
            }
            throw new PMForwardException(CONTINUE);
        } else {
            final EntityFilter filter = ctx.getEntityContainer().getFilter();
            filter.clear();
            final EntityInstanceWrapper instance = filter.getInstance();
            for (Field field : ctx.getEntity().getAllFields()) {
                if (field.shouldDisplay(ctx.getOperation().getId())) {
                    proccessField(ctx, field, instance);
                    checkFilterOperation(ctx, filter, field);
                }
            }
            filter.process(ctx.getEntity());
            return true;
        }
    }

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        PaginatedList pmlist = ctx.getList();
        DisplacedList<Object> contents = new DisplacedList<Object>();
        Long total = null;
        ctx.put(PM_LIST_ORDER, pmlist.getOrder());
        ctx.put(PM_LIST_ASC, !pmlist.isDesc());
        contents.addAll((List<Object>) ctx.getEntity().getList(ctx, ctx.getEntityContainer().getFilter(), pmlist.from(), pmlist.rpp()));
        if (!ctx.getEntity().getNoCount()) {
            total = ctx.getEntity().getDataAccess().count(ctx);
        }
        PaginatedList pmList = ctx.getList();
        pmList.setContents(contents);
        pmList.setTotal(total);
    }

    private void checkFilterOperation(final PMStrutsContext ctx, final EntityFilter filter, final Field field) {
        String eid = "filter_oper_f_" + field.getId();
        String oper = ctx.getRequest().getParameter(eid);
        if (oper != null) {
            try {
                int i = Integer.parseInt(oper);
                switch(i){
                    case 0: filter.getFilterOperations().put(field.getId(), FilterOperation.EQ); break;
                    case 1: filter.getFilterOperations().put(field.getId(), FilterOperation.NE); break;
                    case 2: filter.getFilterOperations().put(field.getId(), FilterOperation.LIKE); break;
                    case 3: filter.getFilterOperations().put(field.getId(), FilterOperation.GT); break;
                    case 4: filter.getFilterOperations().put(field.getId(), FilterOperation.GE); break;
                    case 5: filter.getFilterOperations().put(field.getId(), FilterOperation.LT); break;
                    case 6: filter.getFilterOperations().put(field.getId(), FilterOperation.LE); break;
                    default: filter.getFilterOperations().put(field.getId(), FilterOperation.EQ);
                }
            } catch (Exception e) {
                filter.getFilterOperations().put(field.getId(), FilterOperation.EQ);
            }
        } else {
            filter.getFilterOperations().put(field.getId(), FilterOperation.EQ);
        }
    }
}
