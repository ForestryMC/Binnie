package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenLazy {
	public static class Tree extends BinnieWorldGenTree {
		public Tree(ITreeGenData tree) {
			super(tree, 4,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.35f, 0.4f);
			if (width < 1.2) {
				width = 1.55f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
		}
	}
}
