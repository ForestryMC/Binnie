package binnie.core.util;

import net.minecraft.item.ItemStack;

import java.util.*;

public class ItemStackSet implements Set<ItemStack> {
	List<ItemStack> itemStacks;

	public ItemStackSet() {
		itemStacks = new ArrayList<ItemStack>();
	}

	@Override
	public String toString() {
		return this.itemStacks.toString();
	}

	protected ItemStack getExisting(final ItemStack stack) {
		for (final ItemStack stack2 : itemStacks) {
			if (stack2.isItemEqual(stack)) {
				return stack2;
			}
		}
		return null;
	}

	@Override
	public boolean add(final ItemStack e) {
		if (e != null) {
			if (getExisting(e) == null) {
				return itemStacks.add(e.copy());
			}
			final ItemStack existing = this.getExisting(e);
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
		itemStacks.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return o instanceof ItemStack
				&& getExisting((ItemStack) o) != null;
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
		return itemStacks.isEmpty();
	}

	@Override
	public Iterator<ItemStack> iterator() {
		return itemStacks.iterator();
	}

	@Override
	public boolean remove(final Object o) {
		if (this.contains(o)) {
			final ItemStack r = (ItemStack) o;
			final ItemStack existing = this.getExisting(r);
			if (existing.stackSize > r.stackSize) {
				existing.stackSize -= r.stackSize;
			} else {
				itemStacks.remove(existing);
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		boolean addedAll = true;
		for (final Object o : c) {
			final boolean removed = remove(o);
			addedAll = removed && addedAll;
		}
		return false;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.itemStacks.retainAll(c);
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
	public <T> T[] toArray(final T[] a) {
		return itemStacks.toArray(a);
	}
}
