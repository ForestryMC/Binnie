package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenPalm {
	public static class Coconut extends BinnieWorldGenTree {
		public Coconut(ITreeGenData tree) {
			super(tree, 6, 1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;

			this.generateCylinder(new Vector(0, leafSpawn--, 0), this.girth - 1, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--, 0), this.girth + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn, 0), this.girth - 0.6f, 1, this.leaf, false);
		}
	}
}
