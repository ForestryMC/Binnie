// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block.decor;

import binnie.extratrees.ExtraTrees;
import binnie.Binnie;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.PlankType;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import binnie.core.block.IBlockMetadata;

public class BlockMultiFence extends BlockFence implements IBlockMetadata
{
	public BlockMultiFence() {
		this.setBlockName("multifence");
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (final FenceType type : FenceType.values()) {
			itemList.add(WoodManager.getFence(PlankType.VanillaPlanks.SPRUCE, PlankType.VanillaPlanks.BIRCH, type, 1));
		}
	}

	@Override
	public IIcon getIcon(final int side, final int meta) {
		return (FenceRenderer.layer == 0) ? this.getDescription(meta).getPlankType().getIcon() : this.getDescription(meta).getSecondaryPlankType().getIcon();
	}

	@Override
	public String getBlockName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		final IPlankType type1 = this.getDescription(meta).getPlankType();
		final IPlankType type2 = this.getDescription(meta).getSecondaryPlankType();
		final boolean twoTypes = type1 != type2;
		final FenceType type3 = this.getDescription(meta).getFenceType();
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodslab.name" + (twoTypes ? "2" : ""), type3.getPrefix(), type1.getName(), type2.getName());
	}
}
