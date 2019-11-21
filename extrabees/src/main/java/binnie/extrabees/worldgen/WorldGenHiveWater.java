package binnie.extrabees.worldgen;

import forestry.api.apiculture.hives.IHiveGen;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WorldGenHiveWater implements IHiveGen {

	public WorldGenHiveWater() {
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		// get to the ground
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));
		int maxHeight = topPos.getY();
		if (topPos.getY() == 0) {
			return null;
		}

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);

		for (int i = 0; i < 10; i++) {
			pos.setY(world.rand.nextInt(maxHeight));
			if (isValidLocation(world, pos)) {
				return pos;
			}
		}

		return null;
	}


	@Override
	public boolean isValidLocation(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() != Blocks.WATER) {
			return false;
		}

		Material materialBelow = world.getBlockState(pos.down()).getMaterial();
		return materialBelow == Material.SAND ||
				materialBelow == Material.CLAY ||
				materialBelow == Material.GROUND ||
				materialBelow == Material.ROCK;
	}

	@Override
	public boolean canReplace(IBlockState blockState, World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == Blocks.WATER;
	}
}
