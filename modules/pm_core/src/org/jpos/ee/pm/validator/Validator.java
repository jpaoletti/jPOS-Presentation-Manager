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
package org.jpos.ee.pm.validator;

import org.jpos.ee.pm.core.PMContext;

/**This interface represents any validation that can be made to an entity instance or
 * to a specific field of an instance. Any operation may have one or more validators.  
 * 
 * <h2>Simple entity configuration file</h2>
 * <pre>
 * {@code
 * <field>
 *     <id>some_id</id>
 *     ....
 *     <validator class="org.jpos.ee.pm.validator.SomeValidator" />
 * </field>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public interface Validator {
    /**
     * @param ctx The context
     * @return {@link ValidationResult}
     */
    public ValidationResult validate(PMContext ctx); 

}
