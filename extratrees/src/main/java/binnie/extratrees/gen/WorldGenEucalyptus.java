package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenEucalyptus {
	public static class SwampGum extends BinnieWorldGenTree {
		public SwampGum(ITreeGenData tree) {
			super(tree, 14, 3);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = height + 2;
			int weakerBottom = (int) (height * (0.5 + rand.nextFloat() * 0.3F));
			int bottom = (int) (height * (0.45 + rand.nextFloat() * 0.2F));
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.75f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.75f, 1, this.leaf, false);
			while (leafSpawn > weakerBottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1f + (1.75f * rand.nextFloat()), 1, this.leaf, false);
			}
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.75f + (1.25f * rand.nextFloat()), 1, this.leaf, false);
			}
			for (int i = 0; i < 7; ++i) {
				if (rand.nextFloat() > 0.45) {
					leafSpawn--;
					continue;
				}
				this.generateSphere(
						new Vector(rand.nextInt(girth) * (rand.nextBoolean() ? -1 : 1),
								leafSpawn--,
								rand.nextInt(girth) * (rand.nextBoolean() ? -1 : 1)),
						1, this.leaf, false);
			}
		}

	}

	public static class RoseGum extends BinnieWorldGenTree {
		public RoseGum(ITreeGenData tree) {
			super(tree, 9, 3);
		}

		@Override
		protected void generateLeaves() {
			final int offset = (this.girth - 1) / 2;
			for (int x = 0; x < this.girth; ++x) {
				for (int y = 0; y < this.girth; ++y) {
					for (int i = 0; i < 2; ++i) {
						//TODO:
						//this.addBlock(x - offset, i, y - offset, new BlockTypeLog(EnumETLog.Eucalyptus2), true);
					}
				}
			}
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(0.4f, 0.5f) * this.height;
			float width = this.height * this.randBetween(0.05f, 0.1f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.bushiness = 0.5f;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.bushiness = 0.1f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
		}
	}

	public static class RainbowGum extends BinnieWorldGenTree {
		public RainbowGum(ITreeGenData tree) {
			super(tree, 7, 3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(0.5f, 0.6f) * this.height;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
		}
	}
}
