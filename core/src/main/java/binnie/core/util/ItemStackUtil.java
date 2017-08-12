package binnie.core.util;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class ItemStackUtil {

	public static boolean isItemEqual(ItemStack a, ItemStack b, boolean matchDamage, boolean matchNBT) {
		if (a.isEmpty() || b.isEmpty()) {
			return false;
		}
		if (a.getItem() != b.getItem()) {
			return false;
		}
		if (matchDamage && a.getHasSubtypes()) {
			if (a.getItemDamage() == OreDictionary.WILDCARD_VALUE || b.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				return true;
			}
			if (a.getItemDamage() != b.getItemDamage()) {
				return false;
			}
		}
		return !matchNBT || ItemStack.areItemStackTagsEqual(a, b);
	}
}
