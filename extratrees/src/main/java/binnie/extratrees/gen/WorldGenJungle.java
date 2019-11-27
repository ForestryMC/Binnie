package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenJungle {
	public static class Brazilwood extends BinnieWorldGenTree {
		public Brazilwood(ITreeGenData tree) {
			super(tree,4,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.8f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
		}
	}

	public static class Logwood extends BinnieWorldGenTree {
		public Logwood(ITreeGenData tree) {
			super(tree,4,2);
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
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.7f, 1, this.leaf, false);
			}
		}
	}

	public static class Rosewood extends BinnieWorldGenTree {
		public Rosewood(ITreeGenData tree) {
			super(tree, 6, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.2f, 0.25f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.7f, 1, this.leaf, false);
			}
		}
	}

	public static class Purpleheart extends BinnieWorldGenTree {
		public Purpleheart(ITreeGenData tree) {
			super(tree,7,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			float width = this.height * this.randBetween(0.2f, 0.25f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.7f, 1, this.leaf, false);
		}
	}

	public static class OsangeOrange extends BinnieWorldGenTree {
		public OsangeOrange(ITreeGenData tree) {
			super(tree,5,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.2f, 0.25f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width, 1, this.leaf, false);
		}
	}

	public static class OldFustic extends BinnieWorldGenTree {
		public OldFustic(ITreeGenData tree) {
			super(tree,5,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.7f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
		}
	}

	public static class Coffee extends BinnieWorldGenTree {
		public Coffee(ITreeGenData tree) {
			super(tree,3,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.3f, 1, this.leaf, false);
		}
	}

	public static class BrazilNut extends BinnieWorldGenTree {
		public BrazilNut(ITreeGenData tree) {
			super(tree, 7,1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			final float bottom = this.height - 3;
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
		}
	}

	public static class Shrub15 extends BinnieWorldGenTree {
		public Shrub15(ITreeGenData tree) {
			super(tree, 4, 1);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.8f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
		}
	}
}
