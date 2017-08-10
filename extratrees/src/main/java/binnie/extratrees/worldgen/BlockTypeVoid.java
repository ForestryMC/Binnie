package binnie.extratrees.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;

public class BlockTypeVoid implements WorldGenBlockType {

	@Override
	public void setBlock(World world, ITreeGenData tree, BlockPos pos) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
		if (world.getTileEntity(pos) != null) {
			world.removeTileEntity(pos);
		}
	}
}
