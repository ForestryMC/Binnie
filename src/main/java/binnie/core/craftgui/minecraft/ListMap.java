// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

class ListMap<T> implements List<T>
{
	private LinkedHashMap<Integer, T> map;

	ListMap() {
		map = new LinkedHashMap<Integer, T>();
	}

	@Override
	public int size() {
		int i = -1;
		for (final int k : map.keySet()) {
			if (k > i) {
				i = k;
			}
		}
		return i + 1;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean contains(final Object o) {
		return map.containsValue(o);
	}

	@Override
	public Iterator<T> iterator() {
		return map.values().iterator();
	}

	@Override
	public Object[] toArray() {
		return map.values().toArray();
	}

	@Override
	public <P> P[] toArray(final P[] a) {
		return map.values().toArray(a);
	}

	@Override
	public boolean add(final T e) {
		if (get(size()) == null) {
			add(size(), e);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(final Object o) {
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return map.values().containsAll(c);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public T get(final int index) {
		return map.get(index);
	}

	@Override
	public T set(final int index, final T element) {
		map.put(index, element);
		return element;
	}

	@Override
	public void add(final int index, final T element) {
		map.put(index, element);
	}

	@Override
	public T remove(final int index) {
		return null;
	}

	@Override
	public int indexOf(final Object o) {
		for (final Map.Entry<Integer, T> entry : map.entrySet()) {
			if (entry.getValue() == o) {
				return entry.getKey();
			}
		}
		return 0;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return indexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		return null;
	}

	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		return null;
	}
}
