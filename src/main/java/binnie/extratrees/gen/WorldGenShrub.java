package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenShrub {
	public static class Shrub extends WorldGenTree {
		public Shrub(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			float leafSpawn = height;
			float width = height * randBetween(0.7f, 0.9f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn >= 0.0f) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			minHeight = 1;
			height = determineHeight(1, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
