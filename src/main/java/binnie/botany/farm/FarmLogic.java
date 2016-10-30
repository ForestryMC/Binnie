package binnie.botany.farm;

import forestry.api.farming.IFarmLogic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FarmLogic implements IFarmLogic {
	boolean isManual;

	@Override
	public IFarmLogic setManual(final boolean flag) {
		this.isManual = flag;
		return this;
	}

	protected final boolean isAirBlock(World world, BlockPos pos) {
		return world.isAirBlock(pos);
	}

	protected final IBlockState getBlockState(World world, BlockPos pos) {
		return world.getBlockState(pos);
	}
	
	protected final Block getBlock(World world, BlockPos pos) {
		return getBlockState(world, pos).getBlock();
	}

	protected final int getBlockMeta(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		return blockState.getBlock().getMetaFromState(blockState);
	}

	protected final ItemStack getAsItemStack(World world, BlockPos position) {
		return new ItemStack(getBlock(world, position), 1, getBlockMeta(world, position));
	}

	protected final boolean isWaterBlock(World world, BlockPos pos) {
		return getBlock(world, pos) == Blocks.WATER;
	}

	protected final void setBlock(World world, BlockPos pos, final Block block, final int meta) {
		world.setBlockState(pos, block.getStateFromMeta(meta), 2);
	}
}
