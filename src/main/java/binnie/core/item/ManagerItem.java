package binnie.core.item;

import net.minecraft.creativetab.*;

public class ManagerItem {
	public ItemMisc registerMiscItems(IItemMisc[] items, CreativeTabs tab) {
		return new ItemMisc(tab, items);
	}
}
