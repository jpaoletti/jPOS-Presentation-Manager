package org.jpos.ee.pm.converter;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for a composite collection <br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowCompositionConverter">
 *     <operationId>show</operationId>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */

public class ShowCompositionConverter extends ShowCollectionConverter{
	public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		throw new IgnoreConvertionException("");
	}

}
