package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;

import binnie.extrabees.items.types.EnumPropolis;

public class ItemPropolis extends ItemProduct implements IColoredItem {

	public ItemPropolis() {
		super(EnumPropolis.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("propolis");
		setRegistryName("propolis");
	}

	@Override
	public int getColorFromItemstack(final ItemStack itemStack, final int tintIndex) {
		if (tintIndex == 0) {
			return EnumPropolis.get(itemStack).colour[0];
		}
		return EnumPropolis.get(itemStack).colour[1];
	}
}
