package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;

import binnie.core.item.ItemMisc;
import binnie.extrabees.items.types.EnumPropolis;

public class ItemPropolis extends ItemMisc<EnumPropolis> implements IColoredItem {

	public ItemPropolis() {
		super(Tabs.tabApiculture, EnumPropolis.values(), "propolis");
		this.setCreativeTab(Tabs.tabApiculture);
	}

	@Override
	public int getColorFromItemstack(final ItemStack itemStack, final int tintIndex) {
		EnumPropolis type = EnumPropolis.get(itemStack);
		return type.getColor(tintIndex);
	}
}
