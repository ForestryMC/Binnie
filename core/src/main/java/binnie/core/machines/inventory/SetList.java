package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SetList<E> extends ArrayList<E> implements Set<E> {
	private static final long serialVersionUID = 1277112003159980135L;

	@Override
	public boolean add(E e) {
		return !this.contains(e) && super.add(e);
	}

	@Override
	public void add(int index, E e) {
		if (!this.contains(e)) {
			super.add(index, e);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return this.addAll(this.size(), c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		Collection<E> copy = new ArrayList<>(c);
		copy.removeAll(this);
		return super.addAll(index, copy);
	}
}
