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

import java.util.ArrayList;
import java.util.List;

import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.converter.IgnoreConvertionException;
import org.jpos.ee.pm.core.EntityFilter;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.FilterOperation;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.util.DisplacedList;

public class FilterAction extends FieldProcessingActionSupport {

    @Override
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        if (ctx.getParameter(FINISH) == null) {
            if (ctx.getEntityContainer().getFilter() == null) {
                //Creates filter bean and put it in session
                ctx.getEntityContainer().setFilter(ctx.getEntity().getDataAccess().createFilter(ctx));
            }
            throw new PMForwardException(CONTINUE);
        } else {
            final EntityFilter filter = ctx.getEntityContainer().getFilter();
            filter.clear();
            for (Field field : ctx.getEntity().getAllFields()) {
                if (field.shouldDisplay(ctx.getOperation().getId())) {
                    filter.addFilter(field.getId(), getFilterValues(ctx, field), getFilterOperation(ctx, field));
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

    private FilterOperation getFilterOperation(final PMStrutsContext ctx, final Field field) {
        String eid = "filter_oper_f_" + field.getId();
        String oper = ctx.getRequest().getParameter(eid);
        if (oper != null) {
            try {
                int i = Integer.parseInt(oper);
                switch (i) {
                    case 0:
                        return FilterOperation.EQ;
                    case 1:
                        return FilterOperation.NE;
                    case 2:
                        return FilterOperation.LIKE;
                    case 3:
                        return FilterOperation.GT;
                    case 4:
                        return FilterOperation.GE;
                    case 5:
                        return FilterOperation.LT;
                    case 6:
                        return FilterOperation.LE;
                    default:
                        return FilterOperation.EQ;
                }
            } catch (Exception e) {
                return FilterOperation.EQ;
            }
        } else {
            return FilterOperation.EQ;
        }
    }

    private List<Object> getFilterValues(PMStrutsContext ctx, Field field) throws ConverterException {
        final List<String> parameterValues = getParameterValues(ctx,field);
        final List<Object> values = new ArrayList<Object>();
        int i  = 0;
        for (String value : parameterValues) {
            try {
                final Converter converter = field.getConverters().getConverterForOperation(ctx.getOperation().getId());
                Object converted = getConvertedValue(ctx, field, value, null, converter);
                values.add(converted);
            } catch (IgnoreConvertionException e) {
                //Do nothing, just ignore conversion.
            }
            i++;
        }
        return values;
    }
}
