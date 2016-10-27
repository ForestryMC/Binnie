package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class WorldGenSorbus {
	public static class Whitebeam extends WorldGenTree {
		public Whitebeam(final ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.5f, 0.6f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

	}

	public static class Rowan extends WorldGenTree {
		public Rowan(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height + 1;
			final float bottom = this.randBetween(2, 3);
			final float width = this.height * this.randBetween(0.5f, 0.6f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
		}

	}

	public static class ServiceTree extends WorldGenTree {
		public ServiceTree(final ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			int leafSpawn = this.height + 1;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
			while (leafSpawn > 2) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.4f + this.rand.nextFloat() * 0.7f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(8, 6);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
