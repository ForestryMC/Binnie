package binnie.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;

public class ItemStackSet implements Set<ItemStack> {
	protected final List<ItemStack> itemStacks;

	public ItemStackSet() {
		this.itemStacks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return this.itemStacks.toString();
	}

	protected ItemStack getExisting(ItemStack stack) {
		for (ItemStack stack2 : this.itemStacks) {
			if (ItemStack.areItemsEqual(stack2, stack) && ItemStack.areItemStackTagsEqual(stack2, stack)) {
				return stack2;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean add(ItemStack e) {
		if (!e.isEmpty()) {
			ItemStack existing = this.getExisting(e);
			if (existing.isEmpty()) {
				return this.itemStacks.add(e.copy());
			}
			existing.grow(e.getCount());
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends ItemStack> c) {
		boolean addedAll = true;
		for (ItemStack stack : c) {
			addedAll = (this.add(stack) && addedAll);
		}
		return addedAll;
	}

	@Override
	public void clear() {
		this.itemStacks.clear();
	}

	@Override
	public boolean contains(Object o) {
		return o instanceof ItemStack && !this.getExisting((ItemStack) o).isEmpty();
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
		return this.itemStacks.isEmpty();
	}

	@Override
	public Iterator<ItemStack> iterator() {
		return this.itemStacks.iterator();
	}

	@Override
	public boolean remove(Object o) {
		if (this.contains(o)) {
			ItemStack r = (ItemStack) o;
			ItemStack existing = this.getExisting(r);
			if (!existing.isEmpty() && existing.getCount() > r.getCount()) {
				existing.shrink(r.getCount());
			} else {
				this.itemStacks.remove(existing);
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
	public <T> T[] toArray(T[] a) {
		return this.itemStacks.toArray(a);
	}
}
