package org.jpos.ee.pm.core;

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