package binnie.extratrees.block.stairs;

import net.minecraft.item.ItemBlock;

public class ItemETStairs<V extends BlockETStairs> extends ItemBlock{

	public ItemETStairs(final V block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}

}
