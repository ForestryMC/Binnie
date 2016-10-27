package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockType {
	int meta;
	Block block;

	public BlockType(final Block block, final int meta) {
		this.block = block;
		this.meta = meta;
	}

	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {
		world.setBlockState(pos, this.block.getDefaultState(), 2);
		if (world.getTileEntity(pos) != null) {
			world.removeTileEntity(pos);
		}
	}
}
