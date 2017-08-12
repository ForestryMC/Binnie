package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;

import binnie.extrabees.items.types.EnumHoneyDrop;

public class ItemHoneyDrop extends ItemProduct<EnumHoneyDrop> implements IColoredItem {

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_drop");
		setRegistryName("honey_drop");
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		EnumHoneyDrop drop = get(itemStack);
		return drop.getSpriteColour(tintIndex);
	}
}
