package binnie.extratrees.block.slab;

import binnie.extratrees.block.EnumExtraTreeLog;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemETSlab<V extends BlockETSlab> extends ItemSlab {
	EnumExtraTreeLog[] values;

	public ItemETSlab(V block, V doubleBlock) {
		super(block, block, doubleBlock);
		setRegistryName(block.getRegistryName());
		values = block.getVariant().getAllowedValues().toArray(new EnumExtraTreeLog[]{});
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile." + BlockETSlab.BLOCK_NAME + "." + values[stack.getMetadata()].getName();
	}
}