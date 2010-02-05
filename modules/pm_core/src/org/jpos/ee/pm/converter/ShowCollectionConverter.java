package org.jpos.ee.pm.converter;

import java.util.Collection;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.PMLogger;

/**Converter for a collection (1..* aggregation).<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.ShowCollectionConverter">
 *     <operationId>show</operationId>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class ShowCollectionConverter extends Converter {

	public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
		return value;
	}

	public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
		try {
			Collection<?> list = (Collection<?>)EntitySupport.get(einstance.getInstance(), field.getId());
			StringBuilder sb = new StringBuilder();
			sb.append("<ul>");
			for(Object o:list){
				sb.append("<li>");
				sb.append(o.toString());
				sb.append("</li>");
			}
			sb.append("</ul>");
			return sb.toString();
		} catch (Exception e1) {
			PMLogger.error(e1);
			throw new ConverterException("pm_core.converter.not.collection");
		}
	}
}