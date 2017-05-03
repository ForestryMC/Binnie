package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTropical {
	public static class Mango extends WorldGenTree {
		public Mango(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float width = height * randBetween(0.7f, 0.75f);
			float bottom = 2.0f;
			if (width < 1.2) {
				width = 1.55f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width * 0.3f, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), width * 0.5f, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), width * 0.7f, 1, leaf, false);
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width * 0.8f, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), width * 0.9f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f6 = 0.0f;
				float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				generateCylinder(new Vector(f6, h6, 0.0f), width, 1, leaf, false);
			}
			float f7 = 0.0f;
			float h7 = leafSpawn;
			leafSpawn = h7 - 1.0f;
			generateCylinder(new Vector(f7, h7, 0.0f), width - 1.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
