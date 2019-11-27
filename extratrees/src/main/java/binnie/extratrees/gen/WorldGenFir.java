package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenFir {
	public static class DouglasFir extends BinnieWorldGenTree {
		public DouglasFir(ITreeGenData tree) {
			super(tree, 7, 3);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height + 1;
			int patchyBottom = (int) (this.height / 2.5F);
			int bottom = 3 + rand.nextInt(2);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2, 1, this.leaf, false);
			while (leafSpawn > patchyBottom) {
				if (rand.nextFloat() < 0.45F) {
					this.generateCylinder(new Vector((girth - 1) * (rand.nextBoolean() ? -1 : 1), leafSpawn, (girth - 1) * (rand.nextBoolean() ? -1 : 1)), girth + 0.75f, 1, this.leaf, false);
				}
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.9f, 1, this.leaf, false);
			}
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.3f - (rand.nextInt(2)), 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn,0), girth - 0.2f, 1, this.leaf, false);
		}
	}

	public static class SilverFir extends BinnieWorldGenTree {
		public SilverFir(ITreeGenData tree) {
			super(tree,7,3);
		}

		@Override
		protected void generateLeaves() {
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
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class BalsamFir extends BinnieWorldGenTree {
		public BalsamFir(ITreeGenData tree) {
			super(tree, 6, 2);
		}

		@Override
		protected void generateLeaves() {
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
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}
}
