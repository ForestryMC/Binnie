package binnie.extrabees.products;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;
import net.minecraft.item.ItemStack;


public class ItemHoneyDrop extends ItemProduct implements IColoredItem {

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honeyDrop");
		setRegistryName("honeyDrop");

	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		if (tintIndex == 0) {
			return EnumHoneyDrop.get(itemStack).colour[0];
		}
		return EnumHoneyDrop.get(itemStack).colour[1];
	}

}
