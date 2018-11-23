package binnie.extratrees.blocks.decor;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemHedge extends ItemBlock {
	public ItemHedge(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		int meta = item.getItemDamage();
		//TODO Fix to proper name
		return "";//ExtraTreeSpecies.LeafType.values()[meta % 6].descript + ((meta >= 8) ? " Full" : "") + " Hedge";
	}
}
