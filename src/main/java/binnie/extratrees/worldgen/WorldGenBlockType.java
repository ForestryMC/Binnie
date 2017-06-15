package binnie.extratrees.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;

public interface WorldGenBlockType {
	void setBlock(final World world, final ITreeGenData tree, final BlockPos pos);
}
