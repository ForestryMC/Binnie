package binnie.extratrees.blocks.wood;

import binnie.extratrees.wood.WoodManager;
import forestry.api.arboriculture.IWoodType;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemETSlab extends ItemSlab {

	public ItemETSlab(BlockETSlab block, BlockETSlab slab, BlockETSlab doubleSlab) {
		super(block, slab, doubleSlab);
		setRegistryName(block.getRegistryName());
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		BlockETSlab wood = (BlockETSlab) getBlock();
		int meta = itemstack.getMetadata();
		IWoodType woodType = wood.getWoodType(meta);
		return WoodManager.getDisplayName(wood, woodType);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		BlockETSlab wood = (BlockETSlab) getBlock();
		int meta = stack.getMetadata();
		IWoodType woodType = wood.getWoodType(meta);
		return WoodManager.getDisplayName(wood, woodType);
	}
}