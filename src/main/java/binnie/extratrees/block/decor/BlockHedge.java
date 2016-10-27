package binnie.extratrees.block.decor;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockHedge extends Block implements IBlockFence {
    public BlockHedge() {
        super(Material.LEAVES);
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setRegistryName("hedge");
    }

//	@Override
//	public void addCollisionBoxesToList(final World world, final int x, final int y, final int z, final AxisAlignedBB aabb, final List list, final Entity p_149743_7_) {
//		final boolean connectNegZ = this.canConnectFenceTo(world, x, y, z - 1);
//		final boolean connectPosZ = this.canConnectFenceTo(world, x, y, z + 1);
//		final boolean connectNegX = this.canConnectFenceTo(world, x - 1, y, z);
//		final boolean connectPosX = this.canConnectFenceTo(world, x + 1, y, z);
//		float f = 0.25f;
//		float f2 = 0.75f;
//		float f3 = 0.25f;
//		float f4 = 0.75f;
//		if (connectNegZ) {
//			f3 = 0.0f;
//		}
//		if (connectPosZ) {
//			f4 = 1.0f;
//		}
//		if (connectNegZ || connectPosZ) {
//			this.setBlockBounds(f, 0.0f, f3, f2, 1.5f, f4);
//			super.addCollisionBoxesToList(world, x, y, z, aabb, list, p_149743_7_);
//		}
//		f3 = 0.25f;
//		f4 = 0.75f;
//		if (connectNegX) {
//			f = 0.0f;
//		}
//		if (connectPosX) {
//			f2 = 1.0f;
//		}
//		if (connectNegX || connectPosX || (!connectNegZ && !connectPosZ)) {
//			this.setBlockBounds(f, 0.0f, f3, f2, 1.5f, f4);
//			super.addCollisionBoxesToList(world, x, y, z, aabb, list, p_149743_7_);
//		}
//		if (connectNegZ) {
//			f3 = 0.0f;
//		}
//		if (connectPosZ) {
//			f4 = 1.0f;
//		}
//		this.setBlockBounds(f, 0.0f, f3, f2, 1.0f, f4);
//	}

//	@Override
//	public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
//		final boolean connectNegZ = this.canConnectFenceTo(world, x, y, z - 1);
//		final boolean connectPosZ = this.canConnectFenceTo(world, x, y, z + 1);
//		final boolean connectNegX = this.canConnectFenceTo(world, x - 1, y, z);
//		final boolean connectPosX = this.canConnectFenceTo(world, x + 1, y, z);
//		float f = 0.25f;
//		float f2 = 0.75f;
//		float f3 = 0.25f;
//		float f4 = 0.75f;
//		if (connectNegZ) {
//			f3 = 0.0f;
//		}
//		if (connectPosZ) {
//			f4 = 1.0f;
//		}
//		if (connectNegX) {
//			f = 0.0f;
//		}
//		if (connectPosX) {
//			f2 = 1.0f;
//		}
//		this.setBlockBounds(f, 0.0f, f3, f2, 1.0f, f4);
//	}

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
//	public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
//		return false;
//	}

//	@Override
//	public int getRenderType() {
//		return ModuleBlocks.hedgeRenderID;
//	}
//
//	public boolean canConnectFenceTo(final IBlockAccess p_149826_1_, final int p_149826_2_, final int p_149826_3_, final int p_149826_4_) {
//		final Block block = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
//		return block == this || block == Blocks.fence_gate || func_149825_a(block) || block.isLeaves(p_149826_1_, p_149826_2_, p_149826_3_, p_149826_4_) || (block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd);
//	}
//
//	public static boolean func_149825_a(final Block p_149825_0_) {
//		return BlockFence.canConnect(p_149825_0_);
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
//		return true;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister p_149651_1_) {
//	}

//    private ExtraTreeSpecies.LeafType getType(final int meta) {
//        return ExtraTreeSpecies.LeafType.values()[meta % 8];
//    }

    private boolean isFull(final int meta) {
        return meta / 8 > 0;
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final int p_149691_1_, final int meta) {
//		final ExtraTreeSpecies.LeafType type = this.getType(meta);
//		return ForestryAPI.textureManager.getIcon(this.isFull(meta) ? type.plainUID : type.fancyUID);
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < 6; ++i) {
            for (int f = 0; f < 2; ++f) {
                list.add(new ItemStack(item, 1, i + f * 8));
            }
        }
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public int colorMultiplier(final IBlockAccess world, final int x, final int y, final int z) {
//		return getColor(world.getBlockMetadata(x, y, z));
//	}

    public static int getColor(final int meta) {
        //TODO fix color
//        final ExtraTreeSpecies.LeafType type = ExtraTreeSpecies.LeafType.values()[meta % 6];
//        if (type == ExtraTreeSpecies.LeafType.Conifer) {
//            return ColorizerFoliage.getFoliageColorPine();
//        }
        final double d0 = 0.5;
        final double d2 = 1.0;
        return ColorizerFoliage.getFoliageColor(d0, d2);
    }
}
