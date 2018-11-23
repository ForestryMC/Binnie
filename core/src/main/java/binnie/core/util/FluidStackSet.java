package binnie.core.util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraftforge.fluids.FluidStack;

class FluidStackSet implements Set<FluidStack> {
	protected final List<FluidStack> fluidStacks;

	FluidStackSet() {
		this.fluidStacks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return this.fluidStacks.toString();
	}

	@Nullable
	protected FluidStack getExisting(FluidStack stack) {
		for (FluidStack stack2 : this.fluidStacks) {
			if (stack2.isFluidEqual(stack)) {
				return stack2;
			}
		}
		return null;
	}

	@Override
	public boolean add(FluidStack e) {
		if (e != null) {
			FluidStack existing = this.getExisting(e);
			if (existing == null) {
				return this.fluidStacks.add(e.copy());
			}
			existing.amount += e.amount;
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends FluidStack> c) {
		boolean addedAll = true;
		for (FluidStack stack : c) {
			addedAll = (this.add(stack) && addedAll);
		}
		return addedAll;
	}

	@Override
	public void clear() {
		this.fluidStacks.clear();
	}

	@Override
	public boolean contains(Object o) {
		return o instanceof FluidStack && this.getExisting((FluidStack) o) != null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean addedAll = true;
		for (Object o : c) {
			addedAll = (addedAll && this.contains(o));
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.fluidStacks.isEmpty();
	}

	@Override
	public Iterator<FluidStack> iterator() {
		return this.fluidStacks.iterator();
	}

	@Override
	public boolean remove(Object o) {
		if (this.contains(o)) {
			FluidStack r = (FluidStack) o;
			FluidStack existing = this.getExisting(r);
			if (existing != null && existing.amount > r.amount) {
				existing.amount -= r.amount;
			} else {
				this.fluidStacks.remove(existing);
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean addedAll = true;
		for (Object o : c) {
			boolean removed = this.remove(o);
			addedAll = (removed && addedAll);
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.fluidStacks.retainAll(c);
	}

	@Override
	public int size() {
		return this.fluidStacks.size();
	}

	@Override
	public Object[] toArray() {
		return this.fluidStacks.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.fluidStacks.toArray(a);
	}
}
