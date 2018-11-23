package binnie.extratrees.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.worldgen.TreeBlockTypeLeaf;
import forestry.core.worldgen.WorldGenHelper;

public class WorldGenConifer {
	public static class WesternHemlock extends forestry.arboriculture.worldgen.WorldGenTree {
		public WesternHemlock(ITreeGenData tree) {
			super(tree, 7, 3);
		}

		@Override
		protected void generateLeaves(World world, Random rand, TreeBlockTypeLeaf leaf, List<BlockPos> branchEnds, BlockPos startPos) {
			float leafSpawn = this.height + girth * 4;
			float bottom = WorldGenUtils.randBetween(rand, 2, 3);
			float coneHeight = leafSpawn - bottom;
			float width = (girth * height / 3);
			if (width > 9) {
				width = 9;
			}
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, radius, 1, WorldGenHelper.EnumReplaceMode.AIR);
			}
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn--, 0), girth, girth + width * 0.7F, 1, WorldGenHelper.EnumReplaceMode.AIR);
			WorldGenHelper.generateCylinderFromTreeStartPos(world, leaf, startPos.add(0, leafSpawn, 0), girth, girth + width * 0.4F, 1, WorldGenHelper.EnumReplaceMode.AIR);
		}
	}

	public static class Cypress extends WorldGenTree {
		public Cypress(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float bottom = 1.0f;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width - 1.0f;
				++radius;
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

	public static class Yew extends WorldGenTree {
		public Yew(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= 2.0f - radius;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Cedar extends WorldGenTree {
		public Cedar(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 3;
			float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.girth, 1, this.leaf, false);
			}
			for (leafSpawn = this.height + 3; leafSpawn > bottom; leafSpawn += 1 + this.rand.nextInt(2)) {
				float cone = 1.0f - (leafSpawn - bottom) / coneHeight;
				float radius = (0.7f + this.rand.nextFloat() * 0.3f) * width;
				float xOffset = (-width + this.rand.nextFloat() * 2.0f * width) / 2.0f;
				float yOffset = (-width + this.rand.nextFloat() * 2.0f * width) / 2.0f;
				cone *= 2.0f - cone;
				xOffset *= cone;
				yOffset *= cone;
				radius *= cone;
				if (radius < 2.0f) {
					radius = 2.0f;
				}
				if (xOffset > radius / 2.0f) {
					xOffset = radius / 2.0f;
				}
				if (yOffset > radius / 2.0f) {
					yOffset = radius / 2.0f;
				}
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.7f * radius, 1, this.leaf, false);
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), radius, 1, this.leaf, false);
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.5f * radius, 1, this.leaf, false);
			}

			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class LoblollyPine extends WorldGenTree {
		public LoblollyPine(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float bottom = this.height * this.randBetween(0.65f, 0.7f);
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class MonkeyPuzzle extends WorldGenTree {
		public MonkeyPuzzle(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float width = this.height * this.randBetween(0.4f, 0.45f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.35f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.55f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.75f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.5f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(9, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
