package binnie.core.item;

import net.minecraft.item.ItemStack;

public interface IItemEnum {
	boolean isActive();

	String getName(final ItemStack itemStack);

	int ordinal();

	ItemStack get(final int p0);
}
