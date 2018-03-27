package binnie.extrabees.worldgen;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.apiculture.hives.IHiveGen;

public class WorldGenHiveWater implements IHiveGen {

	public WorldGenHiveWater() {
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		// get to the ground
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));
		if (topPos.getY() == 0) {
			return null;
		}

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);

		while (!isValidLocation(world, pos)) {
			pos.move(EnumFacing.DOWN);
			if (pos.getY() <= 0) {
				return null;
			}
		}

		return pos;
	}

	@Override
	public boolean isValidLocation(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() != Blocks.WATER) {
			return false;
		}
		if (world.getBlockState(pos.down()).getMaterial() == Material.SAND ||
				world.getBlockState(pos.down()).getMaterial() == Material.CLAY ||
				world.getBlockState(pos.down()).getMaterial() == Material.GROUND ||
				world.getBlockState(pos.down()).getMaterial() == Material.ROCK) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canReplace(IBlockState blockState, World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == Blocks.WATER;
	}
}
