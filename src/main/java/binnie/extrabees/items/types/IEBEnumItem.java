package binnie.extrabees.items.types;

import net.minecraft.item.ItemStack;

public interface IEBEnumItem {

	boolean isActive();

	String getName(final ItemStack p0);

	int ordinal();

	ItemStack get(final int p0);

	String name();
}
