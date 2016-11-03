package binnie.extratrees.block.log;

import binnie.extratrees.block.EnumExtraTreeLog;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemETLog <V extends BlockETLog> extends ItemBlock {
	EnumExtraTreeLog[] values;

	public ItemETLog(V block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		setRegistryName(block.getRegistryName());
		values = block.getVariant().getAllowedValues().toArray(new EnumExtraTreeLog[]{});
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile." + BlockETLog.BLOCK_NAME + "." + values[stack.getMetadata()].getName();
	}

}
