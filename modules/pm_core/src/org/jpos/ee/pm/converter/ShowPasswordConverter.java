package org.jpos.ee.pm.converter;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

public class ShowPasswordConverter extends Converter {

	public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		throw new IgnoreConvertionException("");
	}

	public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
		return "**********************";
	}
}