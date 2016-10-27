package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenTree3 {
	public static class Hazel extends WorldGenTree {
		public Hazel(final ITreeGenData tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
			}
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.3f, 1, this.leaf, false);
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.6f, 1, this.leaf, false);
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), width - 1.2f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Sycamore extends WorldGenTree {
		public Sycamore(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width - 1.3f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.6f, 1, this.leaf, false);
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
			}
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.8f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Hawthorn extends WorldGenTree {
		public Hawthorn(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.5f, 1, this.leaf, false);
			}
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Pecan extends WorldGenTree {
		public Pecan(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.6f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.8f * width, 1, this.leaf, false);
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), 0.9f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f5 = 0.0f;
				final float h5 = leafSpawn;
				leafSpawn = h5 - 1.0f;
				this.generateCylinder(new Vector(f5, h5, 0.0f), width, 1, this.leaf, false);
			}
			final float f6 = 0.0f;
			final float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			this.generateCylinder(new Vector(f6, h6, 0.0f), 0.6f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Elm extends WorldGenTree {
		public Elm(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.5f, 1, this.leaf, false);
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
			}
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.5f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Elder extends WorldGenTree {
		public Elder(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width - 1.0f, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), width - 0.4f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
			}
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), width - 0.4f, 1, this.leaf, false);
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), width - 1.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Hornbeam extends WorldGenTree {
		public Hornbeam(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class Sallow extends WorldGenTree {
		public Sallow(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.3f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.6f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.8f * width, 1, this.leaf, false);
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), 0.9f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f5 = 0.0f;
				final float h5 = leafSpawn;
				leafSpawn = h5 - 1.0f;
				this.generateCylinder(new Vector(f5, h5, 0.0f), width, 1, this.leaf, false);
			}
			final float f6 = 0.0f;
			final float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			this.generateCylinder(new Vector(f6, h6, 0.0f), 0.6f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(4, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class AcornOak extends WorldGenTree {
		public AcornOak(final ITree tree) {
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
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), width * 0.4f, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), width * 0.7f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f3 = 0.0f;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, 0.0f), width - 0.2f, 1, this.leaf, false);
				final float f4 = 0.0f;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, 0.0f), width, 1, this.leaf, false);
			}
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), width - 0.5f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
