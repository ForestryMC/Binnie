package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenTilia {
	public static class Basswood extends WorldGenTree {
		public Basswood(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.4f, 0.5f);
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), 0.7f * width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class WhiteBasswood extends WorldGenTree {
		public WhiteBasswood(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.4f, 0.5f);
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), 0.7f * width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class CommonLime extends WorldGenTree {
		public CommonLime(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.45f, 0.55f);
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), 0.7f * width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.4f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 4);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
