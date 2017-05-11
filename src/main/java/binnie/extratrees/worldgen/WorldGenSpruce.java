package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenSpruce {
	public static class GiantSpruce extends WorldGenTree {
		public GiantSpruce(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 3;
			float bottom = randBetween(3, 4);
			float width = height / randBetween(2.5f, 3.0f);
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = 0.15f + 0.85f * radius;
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(15, 4);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class AlpineSpruce extends WorldGenTree {
		public AlpineSpruce(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 5;
			float bottom = randBetween(2, 3);
			float width = height / randBetween(2.0f, 2.5f);
			float coneHeight = leafSpawn - bottom;
			leafSpawn -= 2.0f;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= radius;
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class WhiteSpruce extends WorldGenTree {
		public WhiteSpruce(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(2, 3);
			float width = height / randBetween(2.2f, 2.5f);
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = (float) Math.sqrt(radius);
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class BlackSpruce extends WorldGenTree {
		public BlackSpruce(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(2, 3);
			float width = height / randBetween(2.2f, 2.5f);
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = (float) Math.sqrt(radius);
				radius *= width;
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), radius, 1, leaf, false);
			}
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
