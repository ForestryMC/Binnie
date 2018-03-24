package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTree3 {
	public static class Hazel extends WorldGenTree {
		public Hazel(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 3.0f;
			float width = this.height * this.randBetween(0.45f, 0.5f);
			if (width < 2.5f) {
				width = 2.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.3f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.2f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Sycamore extends WorldGenTree {
		public Sycamore(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height;
			final float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.35f, 0.4f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.3f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.8f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Hawthorn extends WorldGenTree {
		public Hawthorn(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(1, 2);
			float width = this.height * this.randBetween(0.4f, 0.45f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Pecan extends WorldGenTree {
		public Pecan(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.6f, 0.65f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.6f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Elm extends WorldGenTree {
		public Elm(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.45f, 0.5f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Elder extends WorldGenTree {
		public Elder(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 3.0f;
			float width = this.height * this.randBetween(0.55f, 0.6f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Hornbeam extends WorldGenTree {
		public Hornbeam(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.55f, 0.6f);
			if (width < 1.5f) {
				width = 1.5f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Sallow extends WorldGenTree {
		public Sallow(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 2.0f;
			float width = this.height * this.randBetween(0.6f, 0.65f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.6f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class AcornOak extends WorldGenTree {
		public AcornOak(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			final float bottom = 3.0f;
			float width = this.height * this.randBetween(0.45f, 0.5f);
			if (width < 2.0f) {
				width = 2.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.4f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.7f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.2f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
