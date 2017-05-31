package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenMaple {
	public static class RedMaple extends WorldGenTree {
		public RedMaple(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(1, 2);
			final float bottom2 = (this.height + bottom) / 2.0f;
			float width = 2.0f;
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.4f * width, 1, this.leaf, false);
			while (leafSpawn > bottom2) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.4f, 1, this.leaf, false);
			}
			width += 0.6;
			while (leafSpawn > bottom) {
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), width, 1, this.leaf, false);
				final float f5 = 0.0f;
				final float h5 = leafSpawn;
				leafSpawn = h5 - 1.0f;
				this.generateCylinder(new Vector(f5, h5, 0.0f), width - 0.4f, 1, this.leaf, false);
			}
			if (leafSpawn == bottom) {
				final float f6 = 0.0f;
				final float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				this.generateCylinder(new Vector(f6, h6, 0.0f), width - 0.6f, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
