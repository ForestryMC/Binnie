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
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.bushiness = 0.3f;
			while (leafSpawn > weakerBottm) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.0f) * width, 1, this.leaf, false);
			}
			this.bushiness = 0.6f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.8f, 0.9f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, this.leaf, false);
			for (int i = 0; i < 5; ++i) {
				this.generateSphere(new Vector(this.randBetween(-1, 1), leafSpawn--, this.randBetween(-1, 1)), this.randBetween(1, 2), this.leaf, false);
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
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.bushiness = 0.1f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
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
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
