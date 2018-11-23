package binnie.extratrees.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import binnie.core.block.BlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.design.DesignHelper;
import binnie.design.blocks.DesignBlock;
import binnie.extratrees.modules.ModuleCarpentry;

public class BlockCarpentryPanel extends BlockCarpentry {
	public BlockCarpentryPanel() {
		super("carpentryPanel");
		this.useNeighborBrightness = true;
		this.setLightOpacity(0);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		DesignBlock block = this.getCarpentryBlock(source, pos);
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
	public String getDisplayName(ItemStack itemStack) {
		DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
		return I18N.localise("extratrees.block.woodenpanel.name", block.getDesign().getName());
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

	@Override
	public DesignBlock getCarpentryBlock(IBlockAccess world, BlockPos pos) {
		return ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, pos));
	}

	@Override
	public int getPlacedMeta(ItemStack item, World world, BlockPos pos, EnumFacing clickedBlock) {
		DesignBlock block = ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getItemDamage(item));
		EnumFacing facing = clickedBlock;
		boolean valid = true;
		if (!DesignHelper.isValidPanelPlacement(world, pos, facing)) {
			valid = false;
			for (EnumFacing direction : EnumFacing.VALUES) {
				if (DesignHelper.isValidPanelPlacement(world, pos, direction)) {
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
	@SuppressWarnings("deprecation")
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void onNeighborChange(IBlockAccess blockAccess, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(blockAccess, pos, neighbor);
		World world = (World) blockAccess;
		DesignBlock block = this.getCarpentryBlock(blockAccess, pos);
		if (!DesignHelper.isValidPanelPlacement(blockAccess, pos, block.getFacing())) {
			NonNullList<ItemStack> drops = NonNullList.create();
			BlockMetadata.getDrops(drops, this, blockAccess, pos);
			for (ItemStack stack : drops) {
				spawnAsEntity(world, pos, stack);
			}
			world.setBlockToAir(pos);
		}
	}
}
