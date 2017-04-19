package binnie.core.util;

import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
	@Override
	public boolean add(final ItemStack itemStack) {
		return itemStack != null
			&& getExisting(itemStack) == null
			&& itemStacks.add(itemStack.copy());
	}

	@Override
	public boolean remove(final Object object) {
		if (contains(object)) {
			final ItemStack existing = getExisting((ItemStack) object);
			itemStacks.remove(existing);
		}
		return false;
	}
}
