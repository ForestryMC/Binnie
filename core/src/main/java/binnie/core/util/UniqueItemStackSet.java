package binnie.core.util;

import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
	@Override
	public boolean add(ItemStack e) {
		return !e.isEmpty() && this.getExisting(e).isEmpty() && this.itemStacks.add(e.copy());
	}

	@Override
	public boolean remove(Object o) {
		if (this.contains(o)) {
			ItemStack r = (ItemStack) o;
			ItemStack existing = this.getExisting(r);
			this.itemStacks.remove(existing);
		}
		return false;
	}
}
