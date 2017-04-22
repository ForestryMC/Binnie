package binnie.core.item;

import net.minecraft.item.*;

public interface IItemEnum {
	boolean isActive();

	String getName(ItemStack itemStack);

	int ordinal();

	ItemStack get(int count);
}
