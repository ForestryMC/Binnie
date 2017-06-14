package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockType implements WorldGenBlockType {
	IBlockState blockState;

	public BlockType(IBlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public void setBlock(World world, ITreeGenData tree, BlockPos pos) {
		world.setBlockState(pos, blockState, 2);
	}
}
