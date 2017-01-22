package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTropical {
	public static class Mango extends WorldGenTree {
		public Mango(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.7f, 0.75f);
			final float bottom = 2.0f;
			if (width < 1.2) {
				width = 1.55f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width * 0.3f, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), width * 0.5f, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), width * 0.7f, 1, this.leaf, false);
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width * 0.8f, 1, this.leaf, false);
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), width * 0.9f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f6 = 0.0f;
				final float h6 = leafSpawn;
				leafSpawn = h6 - 1.0f;
				this.generateCylinder(new Vector(f6, h6, 0.0f), width, 1, this.leaf, false);
			}
			final float f7 = 0.0f;
			final float h7 = leafSpawn;
			leafSpawn = h7 - 1.0f;
			this.generateCylinder(new Vector(f7, h7, 0.0f), width - 1.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
