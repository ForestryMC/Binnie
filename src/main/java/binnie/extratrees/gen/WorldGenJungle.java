package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenJungle {
	public static class Brazilwood extends WorldGenTree {
		public Brazilwood(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = 1.0f;
			float width = height * randBetween(0.15f, 0.2f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 0.8f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Logwood extends WorldGenTree {
		public Logwood(ITree tree) {
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
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width - 0.7f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Rosewood extends WorldGenTree {
		public Rosewood(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = randBetween(1, 2);
			float width = height * randBetween(0.2f, 0.25f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
				float f4 = 0.0f;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, 0.0f), width - 0.7f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Purpleheart extends WorldGenTree {
		public Purpleheart(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = height - 3;
			float width = height * randBetween(0.2f, 0.25f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, leaf, false);
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width - 0.7f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class OsangeOsange extends WorldGenTree {
		public OsangeOsange(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = randBetween(1, 2);
			float width = height * randBetween(0.2f, 0.25f);
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
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class OldFustic extends WorldGenTree {
		public OldFustic(ITree tree) {
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
			generateCylinder(new Vector(f, h, 0.0f), width - 0.7f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width - 0.5f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(5, 2);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Coffee extends WorldGenTree {
		public Coffee(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = 1.0f;
			float width = height * randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 0.5f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			}
			float f3 = 0.0f;
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(f3, h3, 0.0f), width - 0.3f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(3, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class BrazilNut extends WorldGenTree {
		public BrazilNut(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 1;
			float bottom = height - 3;
			float width = height * randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
			}
			float f4 = 0.0f;
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Shrub15 extends WorldGenTree {
		public Shrub15(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height;
			float bottom = 1.0f;
			float width = height * randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), width - 0.8f, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f2 = 0.0f;
				float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				generateCylinder(new Vector(f2, h2, 0.0f), width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 1);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
