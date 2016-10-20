package binnie.core.item;

import net.minecraft.creativetab.CreativeTabs;

public class ManagerItem {
    public ItemMisc registerMiscItems(final IItemMisc[] items, final CreativeTabs tab) {
        return new ItemMisc(tab, items);
    }
}
