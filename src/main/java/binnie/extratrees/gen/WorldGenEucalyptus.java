package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenEucalyptus {
	public static class SwampGum extends WorldGenTree {
		public SwampGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float weakerBottm = this.randBetween(0.5f, 0.6f) * this.height;
			final float bottom = this.randBetween(0.4f, 0.5f) * this.height;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.bushiness = 0.3f;
			while (leafSpawn > weakerBottm) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), this.randBetween(0.9f, 1.0f) * width, 1, this.leaf, false);
			}
			this.bushiness = 0.6f;
			while (leafSpawn > bottom) {
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), this.randBetween(0.8f, 0.9f) * width, 1, this.leaf, false);
			}
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), 0.5f * width, 1, this.leaf, false);
			for (int i = 0; i < 5; ++i) {
				final float f6 = this.randBetween(-1, 1);
				final float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				this.generateSphere(new Vector(f6, h6, this.randBetween(-1, 1)), this.randBetween(1, 2), this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(14, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class RoseGum extends WorldGenTree {
		public RoseGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.bushiness = 0.1f;
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(9, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class RainbowGum extends WorldGenTree {
		public RainbowGum(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(0.5f, 0.6f) * this.height;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
