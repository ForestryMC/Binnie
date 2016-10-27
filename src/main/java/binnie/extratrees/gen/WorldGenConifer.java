package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenConifer {
	public static class WesternHemlock extends WorldGenTree {
		public WesternHemlock(final ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 6;
			final float bottom = this.randBetween(2, 3);
			float width = this.height / this.randBetween(2.0f, 2.5f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
			}
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class Cypress extends WorldGenTree {
		public Cypress(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = 1.0f;
			float width = this.height * this.randBetween(0.15f, 0.2f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width - 1.0f;
				++radius;
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
			}
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class Yew extends WorldGenTree {
		public Yew(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
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
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), radius, 1, this.leaf, false);
			}
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class Cedar extends WorldGenTree {
		public Cedar(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 3;
			final float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				final float f = 0.0f;
				final float h = leafSpawn;
				leafSpawn = h - 1.0f;
				this.generateCylinder(new Vector(f, h, 0.0f), this.girth, 1, this.leaf, false);
			}
			for (leafSpawn = this.height + 3; leafSpawn > bottom; leafSpawn += 1 + this.rand.nextInt(2)) {
				float cone = 1.0f - (leafSpawn - bottom) / coneHeight;
				float radius = (0.7f + this.rand.nextFloat() * 0.3f) * width;
				float xOffset = (-width + this.rand.nextFloat() * 2.0f * width) / 2.0f;
				float yOffset = (-width + this.rand.nextFloat() * 2.0f * width) / 2.0f;
				cone *= 2.0f - cone;
				xOffset *= cone;
				yOffset *= cone;
				radius *= cone;
				if (radius < 2.0f) {
					radius = 2.0f;
				}
				if (xOffset > radius / 2.0f) {
					xOffset = radius / 2.0f;
				}
				if (yOffset > radius / 2.0f) {
					yOffset = radius / 2.0f;
				}
				final float f2 = xOffset;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, yOffset), 0.7f * radius, 1, this.leaf, false);
				final float f3 = xOffset;
				final float h3 = leafSpawn;
				leafSpawn = h3 - 1.0f;
				this.generateCylinder(new Vector(f3, h3, yOffset), radius, 1, this.leaf, false);
				final float f4 = xOffset;
				final float h4 = leafSpawn;
				leafSpawn = h4 - 1.0f;
				this.generateCylinder(new Vector(f4, h4, yOffset), 0.5f * radius, 1, this.leaf, false);
			}
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class LoblollyPine extends WorldGenTree {
		public LoblollyPine(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.height * this.randBetween(0.65f, 0.7f);
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.6f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				final float f2 = 0.0f;
				final float h2 = leafSpawn;
				leafSpawn = h2 - 1.0f;
				this.generateCylinder(new Vector(f2, h2, 0.0f), width, 1, this.leaf, false);
			}
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}

	public static class MonkeyPuzzle extends WorldGenTree {
		public MonkeyPuzzle(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.4f, 0.45f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float f = 0.0f;
			final float h = leafSpawn;
			leafSpawn = h - 1.0f;
			this.generateCylinder(new Vector(f, h, 0.0f), 0.35f * width, 1, this.leaf, false);
			final float f2 = 0.0f;
			final float h2 = leafSpawn;
			leafSpawn = h2 - 1.0f;
			this.generateCylinder(new Vector(f2, h2, 0.0f), 0.55f * width, 1, this.leaf, false);
			final float f3 = 0.0f;
			final float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			this.generateCylinder(new Vector(f3, h3, 0.0f), 0.75f * width, 1, this.leaf, false);
			final float f4 = 0.0f;
			final float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(f4, h4, 0.0f), 0.9f * width, 1, this.leaf, false);
			final float f5 = 0.0f;
			final float h5 = leafSpawn;
			leafSpawn = h5 - 1.0f;
			this.generateCylinder(new Vector(f5, h5, 0.0f), 1.0f * width, 1, this.leaf, false);
			final float f6 = 0.0f;
			final float h6 = leafSpawn;
			leafSpawn = h6 - 1.0f;
			this.generateCylinder(new Vector(f6, h6, 0.0f), 0.5f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(9, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}

	}
}
