package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenAsh {
	public static class CommonAsh extends WorldGenTree {
		public CommonAsh(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.35f, 0.45f);
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
