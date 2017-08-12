package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;

import binnie.extrabees.items.types.EnumHoneyDrop;

public class ItemHoneyDrop extends ItemProduct implements IColoredItem {

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_drop");
		setRegistryName("honey_drop");
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		if (tintIndex == 0) {
			return EnumHoneyDrop.get(itemStack).getColour()[0];
		}
		return EnumHoneyDrop.get(itemStack).getColour()[1];
	}
}
