package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenWalnut {
	// TODO: unused?
	public static class BlackWalnut extends BinnieWorldGenTree {
		public BlackWalnut(ITreeGenData tree) {
			super(tree, 9, 6);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height + 1;
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.6f, 1, this.leaf, false);
			while (leafSpawn > 3) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.8f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.7f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn,0), girth - 0.2f, 1, this.leaf, false);
		}
	}

	public static class Butternut extends BinnieWorldGenTree {
		public Butternut(ITreeGenData tree) {
			super(tree, 6, 3);
		}

		@Override
		protected void generateLeaves() {
			int leafSpawn = this.height + 1;
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.9f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.9f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.9f, 1, this.leaf, false);
			while (leafSpawn > 3) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.9f, 1, this.leaf, false);
			}
			if (rand.nextBoolean()) {
				this.generateCylinder(new Vector(0, leafSpawn,0), girth + 1.9f, 1, this.leaf, false);
			}
		}
	}
}
