package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTypeLog implements WorldGenBlockType {
	private final ITreeGenData treeGenData;

	public BlockTypeLog(ITreeGenData treeGenData) {
		this.treeGenData = treeGenData;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos, Random random) {
		treeGenData.setLogBlock(world, pos, EnumFacing.UP);
	}
}
