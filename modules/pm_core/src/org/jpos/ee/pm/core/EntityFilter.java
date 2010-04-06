/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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
 */package org.jpos.ee.pm.core;


public class EntityFilter extends PMCoreObject {
    public static final String __EQ__ = "eq";
    public static final String __LT__ = "lt";
    public static final String __LE__ = "le";
    public static final String __GT__ = "gt";
    public static final String __GE__ = "ge";
    public static final String __NE__ = "ne";
    public static final String __BETWEEN__ = "between";    
    
    private EntityInstanceWrapper instance;
    
    public EntityFilter(){
        
    }

    public void process(Entity entity){

    }

    public void clear() {
        
    }
    
    public void setInstance(EntityInstanceWrapper instance) {
        this.instance = instance;
    }

    public EntityInstanceWrapper getInstance() {
        return instance;
    }

}