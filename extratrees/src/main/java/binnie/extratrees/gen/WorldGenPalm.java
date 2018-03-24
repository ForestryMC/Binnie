package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenPalm {
	public static class Coconut extends forestry.arboriculture.worldgen.WorldGenTree {
		public Coconut(ITreeGenData tree) {
			super(tree, 6, 1);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height + 1;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth - 1, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 0.5F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, girth - 0.6F, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}
}
