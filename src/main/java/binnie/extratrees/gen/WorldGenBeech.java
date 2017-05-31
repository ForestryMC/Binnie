package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldGenBeech {
	public static class CommonBeech extends forestry.arboriculture.worldgen.WorldGenTree {
		public CommonBeech(ITreeGenData tree) {
			super(tree, 5, 3);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(rand, 2, 3);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1.5F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > bottom) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2.5F, 1, WorldGenHelper.EnumReplaceMode.AIR);
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1.85F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1.5F, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class CopperBeech extends CommonBeech {
		public CopperBeech(ITreeGenData tree) {
			super(tree);
		}
	}
}
