package binnie.extratrees.block.wood;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import forestry.api.arboriculture.IWoodType;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.items.ItemBlockWood;

import binnie.extratrees.block.WoodManager;

public class ItemBlockETWood<V extends Block & IWoodTyped> extends ItemBlockWood<V> {

	public ItemBlockETWood(V block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		IWoodTyped wood = getBlock();
		int meta = itemstack.getMetadata();
		IWoodType woodType = wood.getWoodType(meta);
		return WoodManager.getDisplayName(wood, woodType);
	}
}
