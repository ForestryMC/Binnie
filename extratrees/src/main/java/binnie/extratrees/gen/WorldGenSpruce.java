package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenSpruce {
	public static class GiantSpruce extends BinnieWorldGenTree {
		public GiantSpruce(final ITree tree) {
			super(tree,15,4);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 3;
			final float bottom = this.randBetween(3, 4);
			final float width = this.height / this.randBetween(2.5f, 3.0f);
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = 0.15f + 0.85f * radius;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class AlpineSpruce extends BinnieWorldGenTree {
		public AlpineSpruce(final ITree tree) {
			super(tree, 5,3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 5;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height / this.randBetween(2.0f, 2.5f);
			final float coneHeight = leafSpawn - bottom;
			leafSpawn -= 2.0f;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius *= radius;
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}
	}

	public static class WhiteSpruce extends BinnieWorldGenTree {
		public WhiteSpruce(final ITree tree) {
			super(tree, 6,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height / this.randBetween(2.2f, 2.5f);
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = (float) Math.sqrt(radius);
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}

	public static class BlackSpruce extends BinnieWorldGenTree {
		public BlackSpruce(final ITree tree) {
			super(tree,6,2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 2;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height / this.randBetween(2.2f, 2.5f);
			final float coneHeight = leafSpawn - bottom;
			while (leafSpawn > bottom) {
				float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
				radius = (float) Math.sqrt(radius);
				radius *= width;
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, this.leaf, false);
		}
	}
}
