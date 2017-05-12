package binnie.extrabees.utils;

import net.minecraft.item.ItemStack;

/**
 * Created by Elec332 on 12-5-2017.
 */
public interface IEBEnumItem {

	boolean isActive();

	String getName(final ItemStack p0);

	int ordinal();

	ItemStack get(final int p0);

	String name();

}
