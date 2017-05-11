package binnie.extratrees.block.decor;

import binnie.extratrees.genetics.LeafType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

// TODO unused class?
public class ItemHedge extends ItemBlockWithMetadata {
	public ItemHedge(Block block) {
		super(block, block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		int meta = item.getItemDamage();
		return LeafType.values()[meta % 6].description + ((meta >= 8) ? " Full" : "") + " Hedge";
	}
}
