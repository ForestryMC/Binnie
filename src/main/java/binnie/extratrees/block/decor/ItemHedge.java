// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block.decor;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;

public class ItemHedge extends ItemBlockWithMetadata
{
	public ItemHedge(final Block block) {
		super(block, block);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack item) {
		final int meta = item.getItemDamage();
		return ExtraTreeSpecies.LeafType.values()[meta % 6].descript + ((meta >= 8) ? " Full" : "") + " Hedge";
	}
}
