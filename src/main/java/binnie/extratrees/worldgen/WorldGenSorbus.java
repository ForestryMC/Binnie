package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenSorbus {
	public static class Whitebeam extends WorldGenTree {
		public Whitebeam(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.5f, 0.6f);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
		}
	}

	public static class Rowan extends WorldGenTree {
		public Rowan(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height + 1;
			float bottom = randBetween(2, 3);
			float width = height * randBetween(0.5f, 0.6f);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
		}
	}

	public static class ServiceTree extends WorldGenTree {
		public ServiceTree(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height + 1;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
			while (leafSpawn > 2) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.4f + rand.nextFloat() * 0.7f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(8, 6);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
