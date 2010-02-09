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

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceWrapper {
	private List<Object> instances;
	
	public EntityInstanceWrapper(){
		instances = new ArrayList<Object>();
	}
	
	public EntityInstanceWrapper(Object o){
		instances = new ArrayList<Object>();
		this.instances.add(o);
	}

	public void setInstance(Object instance) {
		if(instances.size()==0)
			instances.add(instance);
		else
			this.instances.set(0, instance);
	}

	public Object getInstance() {
		return this.instances.get(0);
	}

	public Object getInstance(int i) {
		return this.instances.get(i);
	}

	public List<Object> getInstances() {
		return this.instances;
	}

	public void add(Object o) {
		instances.add(o);
	}
}