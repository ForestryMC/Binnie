package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenHolly {
	public static class Holly extends BinnieWorldGenTree {
		public Holly(ITreeGenData tree) {
			super(tree, 4, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.4f, 0.45f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 1.0f, 1, this.leaf, false);
		}
	}
}
