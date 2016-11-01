package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTypeLog extends BlockType {
	ITreeGenData treeGenData;

	public BlockTypeLog(ITreeGenData treeGenData) {
		super(null, 0);
		this.treeGenData = treeGenData;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {
		treeGenData.setLogBlock(world, pos, EnumFacing.UP);
	}
}
