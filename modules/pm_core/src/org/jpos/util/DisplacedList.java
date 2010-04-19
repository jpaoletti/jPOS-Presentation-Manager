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
 */
package org.jpos.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**This is a list with an index displacement. That means that any index received or
 * sent in functions has a displacement. For example if displacement is 10 then
 * get(10) will return 0 item, get(11) will return 1 item and so on.*/
public class DisplacedList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 455698140739954729L;
	private Integer displacement;

	private void init() {
		displacement=null;
	}
	
	public void add(int index, T element) {
		if(displacement != null)
			super.add(index-displacement, element);
		else
			super.add(index, element);
	}

	public int lastIndexOf(Object o) {
		if(displacement != null)
			return super.lastIndexOf(o) + displacement;
		return super.lastIndexOf(o);
	}

	public T set(int index, T element) {
		if(displacement != null)
			return super.set(index-displacement,element);
		return super.set(index, element);
	}

	public T get(int index) {
		if(displacement != null)
			return super.get(index-displacement);
		return super.get(index);
	}
	
	public int indexOf(Object o) {
		if(displacement != null)
			return super.indexOf(o)+displacement;
		return super.indexOf(o);
	}

	public T remove(int index) {
		if(displacement != null)
			return super.get(index-displacement);
		return super.remove(index);
	}

	public DisplacedList() {
		super();
		init();
	}
	
	public Iterator<T> iterator() {
		return new DisplacedIterator<T>(this);
	}

	public DisplacedList(Integer displacement) {
		super();
		this.displacement = displacement;
	}

	public DisplacedList(Collection<? extends T> c) {
		super(c);
		init();
	}

	public DisplacedList(int initialCapacity) {
		super(initialCapacity);
		init();
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}
}
