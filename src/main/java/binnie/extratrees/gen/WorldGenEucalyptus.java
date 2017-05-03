package binnie.extratrees.gen;

import binnie.extratrees.block.ILogType;
import binnie.extratrees.worldgen.BlockTypeLog;
import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenEucalyptus {
	public static class SwampGum extends WorldGenTree {
		public SwampGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float weakerBottm = randBetween(0.5f, 0.6f) * height;
			float bottom = randBetween(0.4f, 0.5f) * height;
			float width = height * randBetween(0.15f, 0.2f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			bushiness = 0.3f;
			while (leafSpawn > weakerBottm) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.9f, 1.0f) * width, 1, leaf, false);
			}
			bushiness = 0.6f;
			while (leafSpawn > bottom) {
				float f4 = 0.0f;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, 0.0f), randBetween(0.8f, 0.9f) * width, 1, leaf, false);
			}
			float f5 = 0.0f;
			float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			generateCylinder(new Vector(f5, h5, 0.0f), 0.5f * width, 1, leaf, false);
			for (int i = 0; i < 5; ++i) {
				float f6 = randBetween(-1, 1);
				float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				generateSphere(new Vector(f6, h6, randBetween(-1, 1)), randBetween(1, 2), leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(14, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class RoseGum extends WorldGenTree {
		public RoseGum(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			int offset = (girth - 1) / 2;
			for (int x = 0; x < girth; ++x) {
				for (int y = 0; y < girth; ++y) {
					for (int i = 0; i < 2; ++i) {
						addBlock(x - offset, i, y - offset, new BlockTypeLog(ILogType.ExtraTreeLog.Eucalyptus2), true);
					}
				}
			}
			float leafSpawn = height + 2;
			float bottom = randBetween(0.4f, 0.5f) * height;
			float width = height * randBetween(0.05f, 0.1f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			bushiness = 0.5f;
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				bushiness = 0.1f;
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), randBetween(0.9f, 1.1f) * width, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(9, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}

	public static class RainbowGum extends WorldGenTree {
		public RainbowGum(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			generateTreeTrunk(height, girth);
			float leafSpawn = height + 2;
			float bottom = randBetween(0.5f, 0.6f) * height;
			float width = height * randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			float f = 0.0f;
			float h = leafSpawn;
			leafSpawn = h - 1.0f;
			generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, leaf, false);
			float f2 = 0.0f;
			float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, leaf, false);
			while (leafSpawn > bottom) {
				float f3 = 0.0f;
				float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				generateCylinder(new Vector(f3, h3, 0.0f), width, 1, leaf, false);
				float f4 = 0.0f;
				float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			height = determineHeight(7, 3);
			girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
		}
	}
}
