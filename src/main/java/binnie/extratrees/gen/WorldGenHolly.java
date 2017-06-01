package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenHolly {
	public static class Holly extends WorldGenTree {
		public Holly(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.4f, 0.45f);
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
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
			}
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width = 1.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
