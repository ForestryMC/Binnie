package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class WorldGenFir {
	public static class DouglasFir extends forestry.arboriculture.worldgen.WorldGenTree {
		public DouglasFir(ITreeGenData tree) {
			super(tree, 7, 3);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			int leafSpawn = this.height + 1;
			int patchyBottom = (int) (this.height / 2.5F);
			int bottom = 3 + rand.nextInt(2);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			while (leafSpawn > patchyBottom) {
				if (rand.nextFloat() < 0.45F) {
					WorldGenHelper.generateCircle(world, rand, startPos.add((girth - 1) * (rand.nextBoolean() ? -1 : 1), leafSpawn, (girth - 1) * (rand.nextBoolean() ? -1 : 1)), 2, 2, 2, leaf, 0.5f, WorldGenHelper.EnumReplaceMode.SOFT);
				}
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2.9F - (rand.nextBoolean() ? 1 : 0), 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			while (leafSpawn > bottom) {
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 2.3F - (rand.nextBoolean() ? 1 : 0), 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + 1F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, girth - 0.2F, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class SilverFir extends WorldGenTree {
		public SilverFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(2, 3);
			float width = this.height / this.randBetween(2.5f, 3.0f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class BalsamFir extends WorldGenTree {
		public BalsamFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = 1.0f;
			float width = this.height / this.randBetween(2.0f, 2.5f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
