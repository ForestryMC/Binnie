package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTropical {
	public static class Mango extends BinnieWorldGenTree {
		public Mango(ITreeGenData tree) {
			super(tree,5,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.7f, 0.75f);
			final float bottom = 2.0f;
			if (width < 1.2) {
				width = 1.55f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.3f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.7f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.8f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.9f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.0f, 1, this.leaf, false);
		}
	}
}
