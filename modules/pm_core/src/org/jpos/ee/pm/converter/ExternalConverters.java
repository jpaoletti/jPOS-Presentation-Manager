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

/**
 * Collection of external converters
 * 
 * @author jpaoletti
 */
public class ExternalConverters extends PMCoreObject{
    private List<ConverterWrapper> converters;

    public ConverterWrapper getWrapper(String id){
        for (ConverterWrapper cw : converters) {
            if(cw.getId().equalsIgnoreCase(id))
                return cw;
        }
        return null;
    }

    public List<ConverterWrapper> getConverters() {
        return converters;
    }

    public void setConverters(List<ConverterWrapper> converters) {
        this.converters = converters;
    }
    
}