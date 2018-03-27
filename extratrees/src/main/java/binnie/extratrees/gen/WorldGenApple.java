package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenApple {
	public static class OrchardApple extends forestry.arboriculture.worldgen.WorldGenTree {
		public OrchardApple(ITreeGenData tree) {
			super(tree, 3, 6);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 2) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 1F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class SweetCrabapple extends forestry.arboriculture.worldgen.WorldGenTree {
		public SweetCrabapple(ITreeGenData tree) {
			super(tree, 4, 4);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 3) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 2F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 2.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 0.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class FloweringCrabapple extends forestry.arboriculture.worldgen.WorldGenTree {
		public FloweringCrabapple(ITreeGenData tree) {
			super(tree, 3, 6);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 2F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 3) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 3F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 1F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class PrairieCrabapple extends forestry.arboriculture.worldgen.WorldGenTree {
		public PrairieCrabapple(ITreeGenData tree) {
			super(tree, 4, 4);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height;
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 0.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > 3) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.85F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, 1.85F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, 1.5F + girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}
}
