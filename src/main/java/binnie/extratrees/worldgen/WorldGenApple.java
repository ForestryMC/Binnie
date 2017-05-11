package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenApple {
	public static class OrchardApple extends WorldGenTree {
		public OrchardApple(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
			while (leafSpawn > 2) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(3, 6);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class SweetCrabapple extends WorldGenTree {
		public SweetCrabapple(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, leaf, false);
			while (leafSpawn > 3) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 4);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class FloweringCrabapple extends WorldGenTree {
		public FloweringCrabapple(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, leaf, false);
			while (leafSpawn > 3) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.0f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(3, 6);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class PrairieCrabapple extends WorldGenTree {
		public PrairieCrabapple(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int leafSpawn = height;
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, leaf, false);
			while (leafSpawn > 3) {
				generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, leaf, false);
			}
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.0f, 1, leaf, false);
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
		}

		@Override
		public void preGenerate() {
			height = determineHeight(4, 4);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
