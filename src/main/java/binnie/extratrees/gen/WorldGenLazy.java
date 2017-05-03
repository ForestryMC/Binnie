package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenLazy {
	public static class Tree extends WorldGenTree {
		public Tree(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = height / 2 - 1;
			float width = height * randBetween(0.35f, 0.4f);
			if (width < 1.2) {
				width = 1.55f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), width - 0.5f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
