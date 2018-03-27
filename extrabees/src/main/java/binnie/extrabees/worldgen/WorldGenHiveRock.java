package binnie.extrabees.worldgen;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.apiculture.hives.IHiveGen;

public class WorldGenHiveRock implements IHiveGen {

	public WorldGenHiveRock() {
	}

	private boolean hasAirOnOneSide(final World world, final BlockPos pos) {
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos sidePos = pos.offset(facing);
			if (world.isBlockLoaded(sidePos) && world.isAirBlock(sidePos)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		//get to the ground
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));
		if (topPos.getY() == 0) {
			return null;
		}

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);

		while (pos.getY() > 0) {
			pos.move(EnumFacing.DOWN);
			if (isValidLocation(world, pos)) {
				return pos;
			}
		}
		return null;
	}

	@Override
	public boolean isValidLocation(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		if (block.isReplaceableOreGen(blockState, world, pos, BlockStateMatcher.forBlock(Blocks.STONE))) {
			if (hasAirOnOneSide(world, pos)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canReplace(IBlockState blockState, World world, BlockPos pos) {
		Block block = blockState.getBlock();
		return block.isReplaceable(world, pos) && !blockState.getMaterial().isLiquid();
	}
}
