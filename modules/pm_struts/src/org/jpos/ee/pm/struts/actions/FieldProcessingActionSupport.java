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


import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.IgnoreConvertionException;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.ee.pm.validator.ValidationResult;
import org.jpos.ee.pm.validator.Validator;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

public abstract class FieldProcessingActionSupport extends EntityActionSupport {

    protected void proccessField(PMStrutsContext ctx, Field f, EntityInstanceWrapper wrapper) throws PMException {
        String eid = "f_" + f.getId();
        String s = getParamValues(ctx, eid, ";");
        LogEvent evt = PMLogger.getLog().createDebug();
        evt.addMessage("Field [" + eid + "] ");
        int i = 0;
        if(s==null) s="";
        while (s != null) {
            evt.addMessage("    Object to convert: " + s);
            try {
                Object o = wrapper.getInstance(i);
                Converter converter = f.getConverters().getConverterForOperation(ctx.getOperation().getId());
                ctx.put(PM_FIELD, f);
                ctx.put(PM_FIELD_VALUE, s);
                ctx.put(PM_ENTITY_INSTANCE_WRAPPER, wrapper);
                Object converted = converter.build(ctx);
                evt.addMessage("    Object converted: " + converted);
                if (validateField(ctx, f, wrapper, converted)) {
                    PMEntitySupport.set(o, f.getProperty(), converted);
                }
            } catch (IgnoreConvertionException e) {
                //Do nothing, just ignore conversion.
            }
            i++;
            s = getParamValues(ctx, eid + "_" + i, ";");
        }
        Logger.log(evt);
    }

    private boolean validateField(PMStrutsContext ctx, Field field, EntityInstanceWrapper wrapper, Object o) throws PMException {
        boolean ok = true;
        if (field.getValidators() != null) {
            for (Validator fv : field.getValidators()) {
                ctx.put(PM_ENTITY_INSTANCE, wrapper.getInstance());
                ctx.put(PM_FIELD, field);
                ctx.put(PM_FIELD_VALUE, o);
                ValidationResult vr = fv.validate(ctx);
                ctx.getErrors().addAll(vr.getMessages());
                ok = ok && vr.isSuccessful();
            }
        }
        return ok;
    }

    private String getParamValues(PMStrutsContext ctx, String name, String separator) {
        String[] ss = ctx.getRequest().getParameterValues(name);
        if (ss != null) {
            StringBuilder s = new StringBuilder();
            if (ss != null && ss.length > 0) {
                s.append(ss[0]);
            }

            //In this case we have a multivalue input
            for (int i = 1; i < ss.length; i++) {
                s.append(separator);
                s.append(ss[i]);
            }
            return s.toString();
        } else {
            return null;
        }
    }
}
