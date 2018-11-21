package binnie.core.item;

import net.minecraft.item.ItemStack;

public interface IItemSubtype {
	boolean isActive();

	String getDisplayName(ItemStack stack);

	int ordinal();

	ItemStack get(int amount);

	String name();
}
