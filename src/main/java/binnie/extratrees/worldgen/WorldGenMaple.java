package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenMaple {
	public static class RedMaple extends WorldGenTree {
		public RedMaple(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(1, 2);
			float bottom2 = (height + bottom) / 2.0f;
			float width = 2.0f;
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			while (leafSpawn > bottom2) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width - 0.4f, 1, leaf, false);
			}
			width += 0.6;
			while (leafSpawn > bottom) {
				float f4 = 0.0f;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, 0.0f), width, 1, leaf, false);
				float f5 = 0.0f;
				float h5 = leafSpawn;
				leafSpawn = h5 - 1.0f;
				generateCylinder(new Vector(f5, h5, 0.0f), width - 0.4f, 1, leaf, false);
			}
			if (leafSpawn == bottom) {
				float f6 = 0.0f;
				float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				generateCylinder(new Vector(f6, h6, 0.0f), width - 0.6f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
