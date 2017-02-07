package binnie.core.util;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ItemStackSet implements Set<ItemStack> {
	List<ItemStack> itemStacks;

	public ItemStackSet() {
		this.itemStacks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return this.itemStacks.toString();
	}

	@Nullable
	protected ItemStack getExisting(final ItemStack stack) {
		for (final ItemStack stack2 : this.itemStacks) {
			if (ItemStack.areItemsEqual(stack2, stack) && ItemStack.areItemStackTagsEqual(stack2, stack)) {
				return stack2;
			}
		}
		return null;
	}

	@Override
	public boolean add(final ItemStack e) {
		if (e != null) {
			ItemStack existing = this.getExisting(e);
			if (existing == null) {
				return this.itemStacks.add(e.copy());
			}
			existing.stackSize += e.stackSize;
		}
		return false;
	}

	@Override
	public boolean addAll(final Collection<? extends ItemStack> c) {
		boolean addedAll = true;
		for (final ItemStack stack : c) {
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
		return o instanceof ItemStack && this.getExisting((ItemStack) o) != null;
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
	public Iterator<ItemStack> iterator() {
		return this.itemStacks.iterator();
	}

	@Override
	public boolean remove(final Object o) {
		if (this.contains(o)) {
			final ItemStack r = (ItemStack) o;
			final ItemStack existing = this.getExisting(r);
			if (existing != null && existing.stackSize > r.stackSize) {
				existing.stackSize -= r.stackSize;
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
