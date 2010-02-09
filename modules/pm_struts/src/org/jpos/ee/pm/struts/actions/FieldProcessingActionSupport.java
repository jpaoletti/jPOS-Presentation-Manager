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
package org.jpos.ee.pm.struts.actions;

import java.util.Map.Entry;

import org.apache.struts.action.ActionMessage;
import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.IgnoreConvertionException;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.validator.ValidationResult;
import org.jpos.ee.pm.validator.Validator;

public abstract class FieldProcessingActionSupport extends EntityActionSupport{
	
	protected void proccessField(RequestContainer rc, Field f, EntityInstanceWrapper wrapper) {
			try {
				//Object object = rc.getSelected();
				if(getModifiedOwnerCollection(rc, f.getId())!=null){
					PMEntitySupport.set(wrapper.getInstance(), f.getId(), getModifiedOwnerCollection(rc, f.getId()));					
				}else{
	                 String eid = "f_" + f.getId();
	                 debug("Field id: "+eid);
	                 String s = getParamValues(rc, eid, ";");
	                 //debug("Field s: "+s);
	                 int i = 0;	   
	                 while(s != null){
		                debug("Object to convert = "+s);
		                if(!validateField(rc, f, wrapper, s)) return;
		                try {
		                    Object o = wrapper.getInstance(i);
		                	Converter converter = f.getConverters().getConverterForOperation(rc.getOperation().getId());
							Object converted = converter.build(rc.getEntity(), f, rc.getOperation(), wrapper, s);
	                    	debug("Object converted = "+converted);
	                    	PMEntitySupport.set(o, f.getId(), converted);
		                } catch (IgnoreConvertionException e) {
		 					//Do nothing, just ignore conversion.
		  				}
		  				i++;
		  				s = getParamValues(rc, eid+"_"+i, ";");
	                 }
				}
             } catch (Exception e) {
            	 PMLogger.error(e);
                 rc.getErrorlist().put(rc.getEntity().getId(), e.getMessage());
             }
	}

	private boolean validateField(RequestContainer rc, Field field, EntityInstanceWrapper wrapper, String s) {
		if(field.getValidators()!= null){
		     for (Validator fv : field.getValidators()) {
		    	 ValidationResult vr = fv.validate(rc.getEntity(),field,wrapper.getInstance(),s);
		    	 for(Entry<String, String> e :vr.getMessages().entrySet()){
		    		 debug("Validation Result: "+e);
		    		 rc.getErrors().add(e.getKey(), new ActionMessage(e.getValue(),field.getId()));
		    	 }
		    	 //rc.getErrorlist().putAll(vr.getMessages());
		    	 if(! vr.isSuccessful()) return false;
		     }
		 }
		return true;
	}

	private String getParamValues(RequestContainer rc, String name, String separator) {
		String[] ss = rc.getRequest().getParameterValues(name);
		if(ss!=null){
			StringBuilder s = new StringBuilder();
			if(ss!=null && ss.length > 0)s.append(ss[0]);
			
			//In this case we have a multivalue input
			for (int i = 1; i < ss.length; i++) {
				 s.append(separator);
				 s.append(ss[i]);
			}
			return s.toString();
		}else
			return null;
	}
}
