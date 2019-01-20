package binnie.core.item;

import net.minecraft.item.ItemStack;

public interface IItemSubtype {
	boolean isActive();

	String getDisplayName(ItemStack stack);

	int ordinal();

	ItemStack stack(int amount);

	default ItemStack stack() {
		return stack(1);
	}

	String name();
}
