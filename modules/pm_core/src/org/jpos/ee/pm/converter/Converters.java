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
package org.jpos.ee.pm.converter;

import java.util.List;
import org.jpos.ee.pm.core.PMCoreObject;
import org.jpos.ee.pm.core.PresentationManager;

/**Collection of converters*/
public class Converters extends PMCoreObject {

    private List<Converter> converters;
    private List<ExternalConverter> externalConverters;

    /**
     * Looks for an aproppiate converter for the given operation id.
     * @param operId The operation id
     * @return The first converter that matches this operation.
     */
    public Converter getConverterForOperation(String operId) {
        if (getConverters() != null) {
            for (Converter converter : getConverters()) {
                if (check(converter, converter.getOperations(), operId)) {
                    return converter;
                }
            }
        }
        if (getExternalConverters() != null) {
            for (ExternalConverter ecs : getExternalConverters()) {
                final Converter c = PresentationManager.getPm().findExternalConverter(ecs.getId());
                if (check(c, (ecs.getOperations() == null) ? c.getOperations() : ecs.getOperations(), operId)) {
                    //TODO Add override of properties.
                    return c;
                }
            }
        }
        return null;
    }

    protected boolean check(Converter converter, String operations, String operId) {
        return converter != null
                && (operations.trim().equalsIgnoreCase("all")
                || operations.contains(operId));
    }

    public List<ExternalConverter> getExternalConverters() {
        return externalConverters;
    }

    public void setExternalConverters(List<ExternalConverter> externalConverters) {
        this.externalConverters = externalConverters;
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
