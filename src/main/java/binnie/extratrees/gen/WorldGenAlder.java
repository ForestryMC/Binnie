package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;
import forestry.core.worldgen.WorldGenHelper.EnumReplaceMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenAlder {
	public static class CommonAlder extends forestry.arboriculture.worldgen.WorldGenTree {
		public CommonAlder(ITreeGenData tree) {
			super(tree, 5, 2);
		}
		
		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(rand, 1, 2);
			float width = this.height * WorldGenUtils.randBetween(rand, 0.4f, 0.45f);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.3f * width + girth, 1, EnumReplaceMode.SOFT);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.8f * width + girth, 1, EnumReplaceMode.SOFT);
			while (leafSpawn > bottom) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, width - 0.25f + girth, 1, EnumReplaceMode.SOFT);
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, width - 0.4f + girth, 1, EnumReplaceMode.SOFT);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.4f * width + girth, 1, EnumReplaceMode.SOFT);
		}

	}
}
