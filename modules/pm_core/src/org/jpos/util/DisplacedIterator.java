package org.jpos.util;

import java.util.Iterator;

public class DisplacedIterator<T> implements Iterator<T> {
	private Integer displacement;
	private Integer pos = 0;
	private DisplacedList<T> list ;

	public DisplacedIterator(DisplacedList<T> list) {
		this.list = list;
		this.displacement = list.getDisplacement();
		this.pos = displacement;
	}

	public boolean hasNext() {
		return (pos-displacement < list.size());
	}

	public T next() {
		T item = list.get(pos);
		pos++;
		return item;
	}

	public void remove() {}

}
