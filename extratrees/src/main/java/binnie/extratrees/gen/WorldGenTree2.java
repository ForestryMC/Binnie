package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTree2 {
	public static class Olive extends BinnieWorldGenTree {
		public Olive(ITreeGenData tree) {
			super(tree,4,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.35f, 0.4f);
			if (width < 1.2) {
				width = 1.55f;
			}
			this.generateCylinder(new Vector(0.0F, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
		}
	}

	public static class Sweetgum extends BinnieWorldGenTree {
		public Sweetgum(ITreeGenData tree) {
			super(tree,5,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= 2.0f - radius;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class Locust extends BinnieWorldGenTree {
		public Locust(ITreeGenData tree) {
			super(tree,6,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.35f, 0.4f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom + 1.0f) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.3f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.7f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.4f, 1, this.leaf, false);
		}
	}

	public static class Pear extends BinnieWorldGenTree {
		public Pear(ITreeGenData tree) {
			super(tree,3,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
		}
	}

	public static class Iroko extends BinnieWorldGenTree {
		public Iroko(ITreeGenData tree) {
			super(tree,6,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.45f, 0.5f);
			if (width < 2.5f) {
				width = 2.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.25f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.7f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 2.0f, 1, this.leaf, false);
		}
	}

	public static class Gingko extends BinnieWorldGenTree {
		public Gingko(ITreeGenData tree) {
			super(tree,6,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.55f, 0.6f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 2.0f, 1, this.leaf, false);
			while (leafSpawn > bottom * 2.0f + 1.0f) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.5f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.9f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.9f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class Box extends BinnieWorldGenTree {
		public Box(ITreeGenData tree) {
			super(tree,3,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = 0.0f;
			final float width = 1.5f;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
		}
	}

	public static class Clove extends BinnieWorldGenTree {
		public Clove(ITreeGenData tree) {
			super(tree,4,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.4f, 1, this.leaf, false);
		}
	}
}
