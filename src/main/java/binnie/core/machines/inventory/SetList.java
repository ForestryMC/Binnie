package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SetList<E> extends ArrayList<E> implements Set<E> {
    private static final long serialVersionUID = 1277112003159980135L;

    @Override
    public boolean add(final E e) {
        return !this.contains(e) && super.add(e);
    }

    @Override
    public void add(final int index, final E e) {
        if (!this.contains(e)) {
            super.add(index, e);
        }
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        final Collection<E> copy = new ArrayList<E>(c);
        copy.removeAll(this);
        return super.addAll(index, copy);
    }
}
