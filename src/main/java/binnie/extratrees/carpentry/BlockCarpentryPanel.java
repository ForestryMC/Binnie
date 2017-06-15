package binnie.extratrees.carpentry;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;

public class BlockCarpentryPanel extends BlockCarpentry {
	public BlockCarpentryPanel() {
		super("carpentryPanel");
		this.useNeighborBrightness = true;
		this.setLightOpacity(0);
	}

	public static boolean isValidPanelPlacement(IBlockAccess world, BlockPos pos, @Nullable EnumFacing facing) {
		if (facing == null) {
			return false;
		}
		pos = pos.offset(facing);
		IBlockState state = world.getBlockState(pos);
		return state.isSideSolid(world, pos, facing.getOpposite());
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> itemList) {
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		final DesignBlock block = this.getCarpentryBlock(source, pos);
		switch (block.getFacing()) {
			case DOWN: {
				return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
			}
			case EAST: {
				return new AxisAlignedBB(0.9375f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
			}
			case NORTH: {
				return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0625f);
			}
			case SOUTH: {
				return new AxisAlignedBB(0.0f, 0.0f, 0.9375f, 1.0f, 1.0f, 1.0f);
			}
			case UP: {
				return new AxisAlignedBB(0.0f, 0.9375f, 0.0f, 1.0f, 1.0f, 1.0f);
			}
			case WEST: {
				return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 0.0625f, 1.0f, 1.0f);
			}
		}
		return FULL_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getItemBoundingBox() {
		return new AxisAlignedBB(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
	}

	@Override
	public String getDisplayName(final ItemStack itemStack) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.woodenpanel.name", block.getDesign().getName());
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
	public DesignBlock getCarpentryBlock(IBlockAccess world, BlockPos pos) {
		return ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, pos));
	}

	@Override
	public int getPlacedMeta(ItemStack item, World world, BlockPos pos, EnumFacing clickedBlock) {
		final DesignBlock block = ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getItemDamage(item));
		EnumFacing facing = clickedBlock;
		boolean valid = true;
		if (!isValidPanelPlacement(world, pos, facing)) {
			valid = false;
			for (EnumFacing direction : EnumFacing.VALUES) {
				if (isValidPanelPlacement(world, pos, direction)) {
					facing = direction;
					valid = true;
					break;
				}
			}
		}
		if (!valid) {
			return -1;
		}
		block.setFacing(facing);
		return block.getBlockMetadata(this.getDesignSystem());
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, pos, neighbor);
		World world = (World) blockAccess;
		final DesignBlock block = this.getCarpentryBlock(blockAccess, pos);
		if (!isValidPanelPlacement(blockAccess, pos, block.getFacing())) {
			for (ItemStack stack : BlockMetadata.getBlockDroppedAsList(this, blockAccess, pos)) {
				spawnAsEntity(world, pos, stack);
			}
			world.setBlockToAir(pos);
		}
	}
}
