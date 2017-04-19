package binnie.core.util;

import net.minecraft.item.ItemStack;

import java.util.*;

public class ItemStackSet implements Set<ItemStack> {
	protected List<ItemStack> itemStacks;

	public ItemStackSet() {
		itemStacks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return itemStacks.toString();
	}

	protected ItemStack getExisting(final ItemStack stack) {
		for (final ItemStack existStack : itemStacks) {
			if (existStack.isItemEqual(stack)) {
				return existStack;
			}
		}
		return null;
	}

	@Override
	public boolean add(final ItemStack itemStack) {
		if (itemStack != null) {
			ItemStack existing = getExisting(itemStack);
			if (existing == null) {
				return itemStacks.add(itemStack.copy());
			}
			existing.stackSize += itemStack.stackSize;
		}
		return false;
	}

	@Override
	public boolean addAll(final Collection<? extends ItemStack> c) {
		boolean addedAll = true;
		for (final ItemStack stack : c) {
			addedAll = add(stack) && addedAll;
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
	public Iterator<ItemStack> iterator() {
		return itemStacks.iterator();
	}

	@Override
	public boolean remove(final Object object) {
		if (this.contains(object)) {
			final ItemStack r = (ItemStack) object;
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
	public boolean removeAll(final Collection<?> collection) {
		boolean addedAll = true;
		for (final Object object : collection) {
			final boolean removed = remove(object);
			addedAll = removed && addedAll;
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
