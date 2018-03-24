package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenPoplar {
	public static class Aspen extends WorldGenTree {
		public Aspen(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height;
			final float bottom = this.randBetween(this.height / 2, this.height / 2 + 1) + 1;
			float width = this.height * this.randBetween(0.25f, 0.35f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.9f, 1.1f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
