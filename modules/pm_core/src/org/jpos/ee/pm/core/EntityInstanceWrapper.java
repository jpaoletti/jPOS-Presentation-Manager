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

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a list of instances of an entity. This usually contains
 * only one instances but a list is supported for complex operations with
 * several instances.
 *
 * @author jpaoletti
 */
public class EntityInstanceWrapper {

    private List<Object> instances;

    /**
     * Default constructor with an empty list of instances
     */
    public EntityInstanceWrapper() {
        instances = new ArrayList<Object>();
    }

    /**
     * Constructor with a list of instances including the given object.
     *
     * @param o The object to insert in the instances list
     */
    public EntityInstanceWrapper(Object o) {
        instances = new ArrayList<Object>();
        this.instances.add(o);
    }

    /**
     *
     * @param instance
     */
    public void setInstance(Object instance) {
        if (instances.isEmpty()) {
            instances.add(instance);
        } else {
            this.instances.set(0, instance);
        }
    }

    /**
     * Getter for the first instance
     * @return The instance
     */
    public Object getInstance() {
        return this.instances.get(0);
    }

    /**
     * Return the instance at the given index
     *
     * @param i The index
     * @return The instance
     */
    public Object getInstance(int i) {
        return this.instances.get(i);
    }

    /**
     * Getter for the list of instances
     * @return The instances
     */
    public List<Object> getInstances() {
        return this.instances;
    }

    /**
     * Add a new instance to the list
     * 
     * @param o
     */
    public void add(Object o) {
        instances.add(o);
    }
}
