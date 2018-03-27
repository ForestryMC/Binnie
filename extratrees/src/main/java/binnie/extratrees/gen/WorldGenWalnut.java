package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.arboriculture.worldgen.WorldGenTree;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenWalnut {
	// TODO: unused?
	public static class BlackWalnut extends WorldGenTree {
		public BlackWalnut(ITreeGenData tree) {
			super(tree, 9, 6);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height + 1;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.6f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 3) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.8f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.7f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 0.2f - girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class Butternut extends WorldGenTree {
		public Butternut(ITreeGenData tree) {
			super(tree, 6, 3);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height + 1;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.9f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.9f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 2.9f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 3) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 2.9f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			if (rand.nextBoolean()) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 1.9f + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
		}
	}
}
