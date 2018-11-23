package binnie.extratrees.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;

public interface WorldGenBlockType {
	void setBlock(World world, ITreeGenData tree, BlockPos pos);
}
