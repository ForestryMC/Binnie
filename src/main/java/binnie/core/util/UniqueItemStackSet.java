package binnie.core.util;

import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
	@Override
	public boolean add(final ItemStack e) {
		return e != null
				&& getExisting(e) == null
				&& itemStacks.add(e.copy());
	}

	@Override
	public boolean remove(final Object o) {
		if (contains(o)) {
			final ItemStack existing = getExisting((ItemStack) o);
			itemStacks.remove(existing);
		}
		return false;
	}
}
