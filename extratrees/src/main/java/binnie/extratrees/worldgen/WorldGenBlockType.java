package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface WorldGenBlockType {
	void setBlock(final World world, final ITreeGenData tree, final BlockPos pos, Random random);
}
