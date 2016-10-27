package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenFir {
	public static class DouglasFir extends WorldGenTree {
		public DouglasFir(final ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float patchyBottom = this.height / 2;
			final float bottom = this.randBetween(3, 4);
			final float width = this.height * this.randBetween(0.35f, 0.4f);
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, this.leaf, false);
			this.bushiness = 0.1f;
			while (leafSpawn > patchyBottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
			this.bushiness = 0.5f;
			while (leafSpawn > bottom) {
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), this.randBetween(0.7f, 1.0f) * width, 1, this.leaf, false);
			}
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float f6 = 0.0f;
			final float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			this.generateCylinder(new Vector(f6, h6, 0.0f), 0.3f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class SilverFir extends WorldGenTree {
		public SilverFir(final ITree tree) {
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
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
			}
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class BalsamFir extends WorldGenTree {
		public BalsamFir(final ITree tree) {
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
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
			}
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
