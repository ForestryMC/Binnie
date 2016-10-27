package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import forestry.api.core.Tabs;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BlockStainedDesign extends BlockDesign {
	public BlockStainedDesign() {
		super(DesignSystem.Glass, Material.GLASS);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName("stainedGlass");
	}

	@Override
	public int quantityDropped(final Random p_149745_1_) {
		return 0;
	}

//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderBlockPass() {
//		return 1;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
//		final Block block2 = p_149646_1_.getBlock(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]);
//		final Block block3 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
//		return block3 != this && block3 != Botany.stained && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
//	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return ModuleCarpentry.getItemStack(this, GlassType.get(0), GlassType.get(1), design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return Binnie.Language.localise(ExtraTrees.instance, "block.stainedglass.name", design.getDesign().getName());
	}
}
