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
package org.jpos.ee.pm.converter;

import java.util.List;
import java.util.Properties;

/**Collection of converters*/
public class Converters {
	private List<Converter> converters;

	public Converter getConverterForOperation(String operId){
		if(getConverters() != null)
		for(Converter converter : getConverters()){
			if(converter.getOperationId().compareTo(operId) == 0)
				return converter;
		}
		Converter c = new ShowStringConverter(); //default
		c.setOperationId(operId);
		c.setProperties(new Properties());
		return c;
	}
	
	/**
	 * @param converters the converters to set
	 */
	public void setConverters(List<Converter> converters) {
		this.converters = converters;
	}

	/**
	 * @return the converters
	 */
	public List<Converter> getConverters() {
		return converters;
	}
}