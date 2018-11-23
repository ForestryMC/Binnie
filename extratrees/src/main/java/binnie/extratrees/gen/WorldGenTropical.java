package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenTropical {
	public static class Mango extends WorldGenTree {
		public Mango(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height;
			float width = this.height * this.randBetween(0.7f, 0.75f);
			float bottom = 2.0f;
			if (width < 1.2) {
				width = 1.55f;
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.3f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.7f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.8f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.9f, 1, this.leaf, false);
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.0f, 1, this.leaf, false);
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 1);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
