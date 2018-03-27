package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;
import forestry.core.worldgen.WorldGenHelper.EnumReplaceMode;

public class WorldGenAlder {
	public static class CommonAlder extends forestry.arboriculture.worldgen.WorldGenTree {
		public CommonAlder(ITreeGenData tree) {
			super(tree, 5, 2);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(rand, 1, 2);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 0.5F, 1, EnumReplaceMode.SOFT);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1.75F, 1, EnumReplaceMode.SOFT);
			while (leafSpawn > bottom) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2.25F, 1, EnumReplaceMode.SOFT);
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2F, 1, EnumReplaceMode.SOFT);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, girth + 1.75F, 1, EnumReplaceMode.SOFT);
		}
	}
}
