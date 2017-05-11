package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenTree2 {
	public static class Olive extends WorldGenTree {
		public Olive(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = randBetween(2, 3);
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

	public static class Sweetgum extends WorldGenTree {
		public Sweetgum(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(1, 2);
			float width = height * randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= 2.0f - radius;
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
			height = determineHeight(5, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Locust extends WorldGenTree {
		public Locust(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = 2.0f;
			float width = height * randBetween(0.35f, 0.4f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			while (leafSpawn > bottom + 1.0f) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width - 0.3f, 1, leaf, false);
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width - 0.7f, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), width - 0.4f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Pear extends WorldGenTree {
		public Pear(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = randBetween(1, 2);
			float width = height * randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(3, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Iroko extends WorldGenTree {
		public Iroko(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.45f, 0.5f);
			if (width < 2.5f) {
				width = 2.5f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width * 0.25f, 1, leaf, false);
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
			generateCylinder(new Vector(f4, h4, 0.0f), width, 1, leaf, false);
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), width - 2.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Gingko extends WorldGenTree {
		public Gingko(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = 2.0f;
			float width = height * randBetween(0.55f, 0.6f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 2.0f, 1, leaf, false);
			while (leafSpawn > bottom * 2.0f + 1.0f) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width - 1.5f, 1, leaf, false);
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width - 0.9f, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f5 = 0.0f;
				float h5 = leafSpawn;
				leafSpawn = h5 - 1.0f;
				generateCylinder(new Vector(f5, h5, 0.0f), width - 0.9f, 1, leaf, false);
				float f6 = 0.0f;
				float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				generateCylinder(new Vector(f6, h6, 0.0f), width, 1, leaf, false);
			}
			float f7 = 0.0f;
			float h7 = leafSpawn;
			leafSpawn = h7 - 1.0f;
			generateCylinder(new Vector(f7, h7, 0.0f), 0.7f * width, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Box extends WorldGenTree {
		public Box(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = 0.0f;
			float width = 1.5f;
			while (leafSpawn > bottom) {
				float f = 0.0f;
				float h = leafSpawn;
				leafSpawn = h - 1.0f;
				generateCylinder(new Vector(f, h, 0.0f), width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(3, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Clove extends WorldGenTree {
		public Clove(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = 2.0f;
			float width = height * randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width - 0.6f, 1, leaf, false);
			}
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width - 0.4f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
