package binnie.core.craftgui.minecraft;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

// TODO: remove this, it's very questionable
class ListMap<T> implements List<T> {
	private LinkedHashMap<Integer, T> map;

	ListMap() {
		this.map = new LinkedHashMap<>();
	}

	@Override
	public int size() {
		int i = -1;
		for (final int k : this.map.keySet()) {
			if (k > i) {
				i = k;
			}
		}
		return i + 1;
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean contains(final Object o) {
		return this.map.containsValue(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.map.values().iterator();
	}

	@Override
	public Object[] toArray() {
		return this.map.values().toArray();
	}

	@Override
	public <P> P[] toArray(final P[] a) {
		return this.map.values().toArray(a);
	}

	@Override
	public boolean add(final T e) {
		if (this.get(this.size()) == null) {
			this.add(this.size(), e);
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
		return this.map.values().containsAll(c);
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
		this.map.clear();
	}

	@Override
	@Nullable
	public T get(final int index) {
		return this.map.get(index);
	}

	@Override
	public T set(final int index, final T element) {
		this.map.put(index, element);
		return element;
	}

	@Override
	public void add(final int index, final T element) {
		this.map.put(index, element);
	}

	@Override
	@Nullable
	public T remove(final int index) {
		return null;
	}

	@Override
	public int indexOf(final Object o) {
		for (final Map.Entry<Integer, T> entry : this.map.entrySet()) {
			if (entry.getValue() == o) {
				return entry.getKey();
			}
		}
		return 0;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return this.indexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		throw new UnsupportedOperationException();
	}
}
