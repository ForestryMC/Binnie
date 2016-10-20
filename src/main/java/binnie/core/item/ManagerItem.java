// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.item;

import net.minecraft.creativetab.CreativeTabs;

public class ManagerItem
{
	public ItemMisc registerMiscItems(final IItemMisc[] items, final CreativeTabs tab) {
		return new ItemMisc(tab, items);
	}
}
