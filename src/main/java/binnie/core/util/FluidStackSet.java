package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

import java.util.*;

class FluidStackSet implements Set<FluidStack> {
	protected List<FluidStack> itemStacks;

	FluidStackSet() {
		itemStacks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return itemStacks.toString();
	}

	protected FluidStack getExisting(final FluidStack stack) {
		for (final FluidStack existStack : itemStacks) {
			if (existStack.isFluidEqual(stack)) {
				return existStack;
			}
		}
		return null;
	}

	@Override
	public boolean add(final FluidStack fluidStack) {
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
	public boolean addAll(final Collection<? extends FluidStack> collection) {
		boolean addedAll = true;
		for (final FluidStack stack : collection) {
			addedAll = add(stack) && addedAll;
		}
		return addedAll;
	}

	@Override
	public void clear() {
		itemStacks.clear();
	}

	@Override
	public boolean contains(final Object object) {
		return object instanceof FluidStack
			&& getExisting((FluidStack) object) != null;
	}

	@Override
	public boolean containsAll(final Collection<?> collection) {
		boolean addedAll = true;
		for (final Object object : collection) {
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
	public boolean remove(final Object object) {
		if (contains(object)) {
			final FluidStack r = (FluidStack) object;
			final FluidStack existing = getExisting(r);
			if (existing.amount > r.amount) {
				existing.amount -= r.amount;
			} else {
				itemStacks.remove(existing);
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> collection) {
		boolean addedAll = true;
		for (final Object object : collection) {
			final boolean removed = this.remove(object);
			addedAll = (removed && addedAll);
		}
		return false;
	}

	@Override
	public boolean retainAll(final Collection<?> collection) {
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
	public <T> T[] toArray(final T[] array) {
		return itemStacks.toArray(array);
	}
}
