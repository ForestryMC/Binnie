package binnie.extrabees.items.types;

import net.minecraft.item.ItemStack;

public interface IEBEnumItem {

	boolean isActive();

	String getName(ItemStack itemStack);

	int ordinal();

	ItemStack get(int amount);

	String name();
}
