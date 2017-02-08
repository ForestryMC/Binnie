package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class BlockMultiFence extends BlockFence implements IBlockMetadata {
	public BlockMultiFence() {
		super("multifence");
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> itemList) {
		for (final FenceType type : FenceType.values()) {
			itemList.add(WoodManager.getFence(PlankType.VanillaPlanks.SPRUCE, PlankType.VanillaPlanks.BIRCH, type, 1));
		}
	}

//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return (FenceRenderer.layer == 0) ? this.getDescription(meta).getPlankType().getIcon() : this.getDescription(meta).getSecondaryPlankType().getIcon();
//	}


	@Override
	public String getDisplayName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		final IPlankType type1 = this.getDescription(meta).getPlankType();
		final IPlankType type2 = this.getDescription(meta).getSecondaryPlankType();
		final boolean twoTypes = type1 != type2;
		final FenceType type3 = this.getDescription(meta).getFenceType();
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.woodslab.name" + (twoTypes ? "2" : ""), type3.getPrefix(), type1.getName(), type2.getName());
	}
}
