package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenTilia {
	public static class Basswood extends WorldGenTree {
		public Basswood(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.4f, 0.5f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			float h4 = leafSpawn;
			leafSpawn = h4 - 1.0f;
			this.generateCylinder(new Vector(0.0f, h4, 0.0f), 0.7f * width, 1, this.leaf, false);
			float h5 = leafSpawn;
			this.generateCylinder(new Vector(0.0f, h5, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class WhiteBasswood extends WorldGenTree {
		public WhiteBasswood(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.4f, 0.5f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(6, 3);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}

	public static class CommonLime extends WorldGenTree {
		public CommonLime(ITree tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 1;
			float bottom = this.randBetween(2, 3);
			float width = this.height * this.randBetween(0.45f, 0.55f);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), this.randBetween(0.95f, 1.05f) * width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(7, 4);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
