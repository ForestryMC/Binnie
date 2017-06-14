package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WorldGenBlockType {
	void setBlock(final World world, final ITreeGenData tree, final BlockPos pos);
}
