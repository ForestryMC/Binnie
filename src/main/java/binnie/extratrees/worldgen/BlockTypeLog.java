package binnie.extratrees.worldgen;

import binnie.extratrees.genetics.WoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.world.ITreeGenData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTypeLog extends BlockType {
	IWoodType log;

	public BlockTypeLog(final IWoodType log) {
		super(null, 0);
		this.log = log;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {

		IBlockState blockState = new WoodAccess().getBlock(log, WoodBlockKind.LOG, false);
		if (blockState == null)
			blockState = TreeManager.woodAccess.getBlock(log, WoodBlockKind.LOG, false);
		world.setBlockState(pos, blockState, 2);
		//this.log.placeBlock(world, new BlockPos(x, y, z));
	}
}
