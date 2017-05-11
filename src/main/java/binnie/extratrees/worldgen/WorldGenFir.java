package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenFir {
	public static class DouglasFir extends WorldGenTree {
		public DouglasFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float patchyBottom = height / 2;
			float bottom = randBetween(3, 4);
			float width = height * randBetween(0.35f, 0.4f);
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, leaf, false);
			bushiness = 0.1f;
			while (leafSpawn > patchyBottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.9f, 1.1f) * width, 1, leaf, false);
			}
			bushiness = 0.5f;
			while (leafSpawn > bottom) {
				float f4 = 0.0f;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, 0.0f), randBetween(0.7f, 1.0f) * width, 1, leaf, false);
			}
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.7f * width, 1, leaf, false);
			float f6 = 0.0f;
			float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			generateCylinder(new Vector(f6, h6, 0.0f), 0.3f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class SilverFir extends WorldGenTree {
		public SilverFir(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(2, 3);
			float width = height / randBetween(2.5f, 3.0f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
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
			height = determineHeight(7, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class BalsamFir extends WorldGenTree {
		public BalsamFir(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = 1.0f;
			float width = height / randBetween(2.0f, 2.5f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
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
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
