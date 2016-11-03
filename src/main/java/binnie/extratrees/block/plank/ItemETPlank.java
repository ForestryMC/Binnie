package binnie.extratrees.block.plank;

import binnie.extratrees.block.EnumExtraTreeLog;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Marcin on 03.11.2016.
 */
public class ItemETPlank<V extends BlockETPlank> extends ItemBlock {
	EnumExtraTreeLog[] values;

	public ItemETPlank(V block) {
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
		return "tile." + BlockETPlank.BLOCK_NAME + "." + values[stack.getMetadata()].getName();
	}
}

