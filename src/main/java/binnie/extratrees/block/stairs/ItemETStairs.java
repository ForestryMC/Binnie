package binnie.extratrees.block.stairs;

import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.WoodManager;
import forestry.api.arboriculture.IWoodType;
import forestry.arboriculture.blocks.BlockForestryStairs;
import forestry.arboriculture.items.ItemBlockWood;
import net.minecraft.item.ItemStack;

public class ItemETStairs<B extends BlockForestryStairs<EnumExtraTreeLog>> extends ItemBlockWood<B>{

	public ItemETStairs(B block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		B wood = getBlock();
		int meta = itemstack.getMetadata();
		IWoodType woodType = wood.getWoodType(meta);
		return WoodManager.getDisplayName(wood, woodType);
	}

}
