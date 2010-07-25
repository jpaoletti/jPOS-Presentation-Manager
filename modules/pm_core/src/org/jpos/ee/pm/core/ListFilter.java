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
package org.jpos.ee.pm.core;

/**
 * An interface that filters the data shown by an entity. Each data access has
 * his own creation of this interface.
 *
 * @author jpaoletti
 * */
public interface ListFilter {

    /**
     * Returns an specific implementation of a filter given by and understood by
     * each data access.
     * 
     * @param ctx The context
     * @return the filter object
     */
    public Object getListFilter(PMContext ctx);
}
