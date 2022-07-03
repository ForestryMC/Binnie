package binnie.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraftforge.fluids.FluidStack;

class FluidStackSet implements Set<FluidStack> {
    protected List<FluidStack> itemStacks;

    FluidStackSet() {
        itemStacks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return itemStacks.toString();
    }

    protected FluidStack getExisting(FluidStack stack) {
        for (FluidStack existStack : itemStacks) {
            if (existStack.isFluidEqual(stack)) {
                return existStack;
            }
        }
        return null;
    }

    @Override
    public boolean add(FluidStack fluidStack) {
        if (fluidStack != null) {
            FluidStack existing = getExisting(fluidStack);
            if (existing == null) {
                return itemStacks.add(fluidStack.copy());
            }
            existing.amount += fluidStack.amount;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends FluidStack> collection) {
        boolean addedAll = true;
        for (FluidStack stack : collection) {
            addedAll = add(stack) && addedAll;
        }
        return addedAll;
    }

    @Override
    public void clear() {
        itemStacks.clear();
    }

    @Override
    public boolean contains(Object object) {
        return object instanceof FluidStack && getExisting((FluidStack) object) != null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean addedAll = true;
        for (Object object : collection) {
            addedAll = addedAll && contains(object);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return itemStacks.isEmpty();
    }

    @Override
    public Iterator<FluidStack> iterator() {
        return itemStacks.iterator();
    }

    @Override
    public boolean remove(Object object) {
        if (contains(object)) {
            FluidStack r = (FluidStack) object;
            FluidStack existing = getExisting(r);
            if (existing.amount > r.amount) {
                existing.amount -= r.amount;
            } else {
                itemStacks.remove(existing);
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean addedAll = true;
        for (Object object : collection) {
            boolean removed = remove(object);
            addedAll = (removed && addedAll);
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return itemStacks.retainAll(collection);
    }

    @Override
    public int size() {
        return itemStacks.size();
    }

    @Override
    public Object[] toArray() {
        return itemStacks.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return itemStacks.toArray(array);
    }
}
