package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;

public class WorldGenWalnut {
	public static class BlackWalnut extends WorldGenTree {
		public BlackWalnut(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height + 1;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.6f, 1, leaf, false);
			while (leafSpawn > randBetween(3, 4)) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.8f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.7f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.8f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(9, 6);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class Butternut extends WorldGenTree {
		public Butternut(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height + 1;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, leaf, false);
			while (leafSpawn > 3) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, leaf, false);
			}
			if (rand.nextBoolean()) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.0f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(6, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
