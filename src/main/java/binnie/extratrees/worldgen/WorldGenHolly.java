package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenHolly {
	public static class Holly extends WorldGenTree {
		public Holly(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = 1.0f;
			float width = height * randBetween(0.4f, 0.45f);
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
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width = 1.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
