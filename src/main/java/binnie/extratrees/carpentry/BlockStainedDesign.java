package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses() {
		return 2;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (blockState != iblockstate) {
            return true;
        }

        if (block == this){
            return false;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return ModuleCarpentry.getItemStack(this, GlassType.get(0), GlassType.get(1), design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.stainedglass.name", design.getDesign().getName());
	}
}
