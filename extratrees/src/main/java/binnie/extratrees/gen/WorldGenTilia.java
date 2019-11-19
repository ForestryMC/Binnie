package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenTilia {
	public static class Basswood extends BinnieWorldGenTree {
		public Basswood(final ITree tree) {
			super(tree,6,3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.4f, 0.5f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(0.0f, h4, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float h5 = leafSpawn;
			this.generateCylinder(new Vector(0.0f, h5, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class WhiteBasswood extends BinnieWorldGenTree {
		public WhiteBasswood(final ITree tree) {
			super(tree,6,3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.4f, 0.5f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class CommonLime extends BinnieWorldGenTree {
		public CommonLime(final ITree tree) {
			super(tree,7,4);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.45f, 0.55f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}
}
