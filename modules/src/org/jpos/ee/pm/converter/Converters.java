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