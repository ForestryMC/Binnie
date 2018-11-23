package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenMaple {
	public static class RedMaple extends WorldGenTree {
		public RedMaple(ITreeGenData tree) {
			super(tree);
		}

		@Override
		public void generate() {
			this.generateTreeTrunk(this.height, this.girth);
			float leafSpawn = this.height + 2;
			float bottom = this.randBetween(1, 2);
			float bottom2 = (this.height + bottom) / 2.0f;
			float width = 2.0f;
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
			while (leafSpawn > bottom2) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, this.leaf, false);
			}
			width += 0.6;
			while (leafSpawn > bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
				this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, this.leaf, false);
			}
			if (leafSpawn == bottom) {
				this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.6f, 1, this.leaf, false);
			}
		}

		@Override
		public void preGenerate() {
			this.height = this.determineHeight(5, 2);
			this.girth = this.determineGirth(this.treeGen.getGirth());
		}
	}
}
