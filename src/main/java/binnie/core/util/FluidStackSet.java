package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

import java.util.*;

class FluidStackSet implements Set<FluidStack> {
    List<FluidStack> itemStacks;

    FluidStackSet() {
        this.itemStacks = new ArrayList<FluidStack>();
    }

    @Override
    public String toString() {
        return this.itemStacks.toString();
    }

    protected FluidStack getExisting(final FluidStack stack) {
        for (final FluidStack stack2 : this.itemStacks) {
            if (stack2.isFluidEqual(stack)) {
                return stack2;
            }
        }
        return null;
    }

    @Override
    public boolean add(final FluidStack e) {
        if (e != null) {
            if (this.getExisting(e) == null) {
                return this.itemStacks.add(e.copy());
            }
            final FluidStack existing = this.getExisting(e);
            existing.amount += e.amount;
        }
        return false;
    }

    @Override
    public boolean addAll(final Collection<? extends FluidStack> c) {
        boolean addedAll = true;
        for (final FluidStack stack : c) {
            addedAll = (this.add(stack) && addedAll);
        }
        return addedAll;
    }

    @Override
    public void clear() {
        this.itemStacks.clear();
    }

    @Override
    public boolean contains(final Object o) {
        return o instanceof FluidStack && this.getExisting((FluidStack) o) != null;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        boolean addedAll = true;
        for (final Object o : c) {
            addedAll = (addedAll && this.contains(o));
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.itemStacks.isEmpty();
    }

    @Override
    public Iterator<FluidStack> iterator() {
        return this.itemStacks.iterator();
    }

    @Override
    public boolean remove(final Object o) {
        if (this.contains(o)) {
            final FluidStack r = (FluidStack) o;
            final FluidStack existing = this.getExisting(r);
            if (existing.amount > r.amount) {
                final FluidStack fluidStack = existing;
                fluidStack.amount -= r.amount;
            } else {
                this.itemStacks.remove(existing);
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean addedAll = true;
        for (final Object o : c) {
            final boolean removed = this.remove(o);
            addedAll = (removed && addedAll);
        }
        return false;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.itemStacks.retainAll(c);
    }

    @Override
    public int size() {
        return this.itemStacks.size();
    }

    @Override
    public Object[] toArray() {
        return this.itemStacks.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return this.itemStacks.toArray(a);
    }
}
