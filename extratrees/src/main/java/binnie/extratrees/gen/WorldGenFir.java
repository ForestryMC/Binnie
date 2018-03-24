package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenFir {
	public static class DouglasFir extends WorldGenTree {
		public DouglasFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float patchyBottom = this.height / 2;
			final float bottom = this.randBetween(3, 4);
			final float width = this.height * this.randBetween(0.35f, 0.4f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			this.bushiness = 0.1f;
			while (leafSpawn > patchyBottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
			this.bushiness = 0.5f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.7f, 1.0f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.3f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class SilverFir extends WorldGenTree {
		public SilverFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(2, 3);
			float width = this.height / this.randBetween(2.5f, 3.0f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class BalsamFir extends WorldGenTree {
		public BalsamFir(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = 1.0f;
			float width = this.height / this.randBetween(2.0f, 2.5f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
