package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenBeech {
	public static class CommonBeech extends WorldGenTree {
		public CommonBeech(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.47f, 0.5f);
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
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), width - 1.0f, 1, this.leaf, false);
		}

	}

	public static class CopperBeech extends CommonBeech {
		public CopperBeech(final ITree tree) {
			super(tree);
		}
	}
}
