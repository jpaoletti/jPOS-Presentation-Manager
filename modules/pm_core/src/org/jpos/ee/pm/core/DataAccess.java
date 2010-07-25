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

import java.util.List;

/**
 * An entity data access offers the basic methods to operate over the data source of an entity.
 * 
 * @author jpaoletti
 * */
public interface DataAccess {

    /**Get an item from data source an item identified by the property=value
     *
     * @param ctx The context
     * @param property The property to look up
     * @param value The value of the property that must be looked for.
     * @return The object or null of not found
     * @throws PMException
     * */
    public Object getItem(PMContext ctx, String property, String value) throws PMException;

    /**Get a filtered list of items from data source with the given items
     * 
     * @param ctx The context
     * @param filter The filter of the list
     * @param from The starting index of the list
     * @param count The maximum number of resultant items
     * @return The list of items
     * @throws PMException
     * */
    public List<?> list(PMContext ctx, EntityFilter filter, Integer from, Integer count) throws PMException;

    /**Get the total number of items of the entity
     * @param ctx The context
     * @return Item total count
     * @throws PMException
     * */
    public Long count(PMContext ctx) throws PMException;

    /**Removes the object from data source
     * 
     * @param ctx The context
     * @param object The object to remove
     * @throws PMException
     * */
    public void delete(PMContext ctx, Object object) throws PMException;

    /**Updates the given object
     * 
     * @param ctx The context
     * @param instance The modified object
     * @throws PMException
     * */
    public void update(PMContext ctx, Object instance) throws PMException;

    /**Adds the object to the data source
     * 
     * @param ctx The context
     * @param instance The new object
     * @throws PMException
     * */
    public void add(PMContext ctx, Object instance) throws PMException;

    /**Synchronize the referenced object with the data source
     * 
     * @param ctx The context
     * @param o The object to refresh
     * @return Fresh object
     * @throws PMException
     * */
    public Object refresh(PMContext ctx, Object o) throws PMException;

    /**Creates a new instance of the specific class of this data access
     * 
     * @param ctx The context
     * @return the new filter
     * @throws PMException
     * */
    public EntityFilter createFilter(PMContext ctx) throws PMException;
}
