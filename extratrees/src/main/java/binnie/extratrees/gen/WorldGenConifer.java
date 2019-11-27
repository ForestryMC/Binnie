package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenConifer {
	public static class WesternHemlock extends BinnieWorldGenTree {
		public WesternHemlock(ITreeGenData tree) {
			super(tree, 7, 3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + girth * 4;
			float bottom = WorldGenUtils.randBetween(rand, 2, 3);
			final float coneHeight = leafSpawn - bottom;
			float width = (girth * height / 3);
			if (width > 9) {
				width = 9;
			}
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= width;
				this.generateCylinder(new Vector(0, leafSpawn--,0), radius,1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + width * 0.7f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn,0), girth + width * 0.4f, 1, this.leaf, false);
		}
	}

	public static class Cypress extends BinnieWorldGenTree {
		public Cypress(ITreeGenData tree) {
			super(tree, 6, 2);
		}

		@Override
		protected void generateLeaves() {
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
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class Yew extends BinnieWorldGenTree {
		public Yew(ITreeGenData tree) {
			super(tree, 5, 2);
		}

		@Override
		protected void generateLeaves() {
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
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class Cedar extends BinnieWorldGenTree {
		public Cedar(ITreeGenData tree) {
			super(tree, 6, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 3;
			final float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.7f, 0.75f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.girth, 1, this.leaf, false);
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
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.7f * radius, 1, this.leaf, false);
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), radius, 1, this.leaf, false);
				this.generateCylinder(new Vector(xOffset, leafSpawn--, yOffset), 0.5f * radius, 1, this.leaf, false);
			}

			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class LoblollyPine extends BinnieWorldGenTree {
		public LoblollyPine(ITreeGenData tree) {
			super(tree, 6, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			final float bottom = this.height * this.randBetween(0.65f, 0.7f);
			float width = this.height * this.randBetween(0.25f, 0.3f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class MonkeyPuzzle extends BinnieWorldGenTree {
		public MonkeyPuzzle(ITreeGenData tree) {
			super(tree,9,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			float width = this.height * this.randBetween(0.4f, 0.45f);
			if (width > 7.0f) {
				width = 7.0f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.35f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.55f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.75f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.5f * width, 1, this.leaf, false);
		}
	}
}
