package binnie.extratrees.blocks.decor;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumLeafType;
import forestry.api.core.Tabs;
import forestry.arboriculture.ModuleArboriculture;
import forestry.core.blocks.IColoredBlock;

public class BlockHedge extends Block implements IBlockFence, IColoredBlock {
	public BlockHedge() {
		super(Material.LEAVES);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName("hedge");
	}

	@SideOnly(Side.CLIENT)
	public static int getColor(int meta) {
		EnumLeafType type = EnumLeafType.values()[meta % 6];
		if (type == EnumLeafType.CONIFERS) {
			return ColorizerFoliage.getFoliageColorPine();
		}
		final double d0 = 0.5;
		final double d2 = 1.0;
		return ColorizerFoliage.getFoliageColor(d0, d2);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		final boolean connectNegZ = this.canConnectFenceTo(world, pos.north());
		final boolean connectPosZ = this.canConnectFenceTo(world, pos.south());
		final boolean connectNegX = this.canConnectFenceTo(world, pos.west());
		final boolean connectPosX = this.canConnectFenceTo(world, pos.east());
		float f = 0.25f;
		float f2 = 0.75f;
		float f3 = 0.25f;
		float f4 = 0.75f;
		if (connectNegZ) {
			f3 = 0.0f;
		}
		if (connectPosZ) {
			f4 = 1.0f;
		}
		if (connectNegZ || connectPosZ) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, 0.0f, f3, f2, 1.5f, f4));
		}
		f3 = 0.25f;
		f4 = 0.75f;
		if (connectNegX) {
			f = 0.0f;
		}
		if (connectPosX) {
			f2 = 1.0f;
		}
		if (connectNegX || connectPosX || (!connectNegZ && !connectPosZ)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, 0.0f, f3, f2, 1.5f, f4));
		}
		if (connectNegZ) {
			f3 = 0.0f;
		}
		if (connectPosZ) {
			f4 = 1.0f;
		}
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(f, 0.0f, f3, f2, 1.0f, f4));
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		final boolean connectNegZ = this.canConnectFenceTo(world, pos.north());
		final boolean connectPosZ = this.canConnectFenceTo(world, pos.south());
		final boolean connectNegX = this.canConnectFenceTo(world, pos.west());
		final boolean connectPosX = this.canConnectFenceTo(world, pos.east());
		float f = 0.25f;
		float f2 = 0.75f;
		float f3 = 0.25f;
		float f4 = 0.75f;
		if (connectNegZ) {
			f3 = 0.0f;
		}
		if (connectPosZ) {
			f4 = 1.0f;
		}
		if (connectNegX) {
			f = 0.0f;
		}
		if (connectPosX) {
			f2 = 1.0f;
		}
		return new AxisAlignedBB(f, 0.0f, f3, f2, 1.0f, f4);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/*@Override
	public int getRenderType() {
		return ModuleBlocks.hedgeRenderID;
	}*/

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	public boolean canConnectFenceTo(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		return block == this || canConnectTo(world, pos) || block.isLeaves(state, world, pos) || (state.getMaterial().isOpaque() && state.isFullCube() && state.getMaterial() != Material.GOURD);
	}

	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if (ModuleArboriculture.validFences.contains(block)) {
			return true;
		}

		if (block != Blocks.BARRIER) {
			Material blockMaterial = state.getMaterial();
			if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof IBlockFence) {
				return blockMaterial == this.blockMaterial;
			}
			return blockMaterial.isOpaque() && state.isFullCube() && blockMaterial != Material.GOURD;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	private EnumLeafType getType(int meta) {
		return EnumLeafType.values()[meta % 8];
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final int p_149691_1_, final int meta) {
		final ExtraTreeSpecies.LeafType type = this.getType(meta);
		return ForestryAPI.textureManager.getIcon(this.isFull(meta) ? type.plainUID : type.fancyUID);
	}*/

	private boolean isFull(final int meta) {
		return meta / 8 > 0;
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < 6; ++i) {
			for (int f = 0; f < 2; ++f) {
				list.add(new ItemStack(this, 1, i + f * 8));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		return getColor(getMetaFromState(state));
	}
}
