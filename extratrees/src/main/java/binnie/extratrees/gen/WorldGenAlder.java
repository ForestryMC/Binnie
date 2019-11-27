package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenAlder {
	public static class CommonAlder extends BinnieWorldGenTree {
		public CommonAlder(ITreeGenData tree) {
			super(tree, 5, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(this.rand, 1, 2);

			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.5f, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.75f, 1, this.leaf, false);
			while(leafSpawn > bottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.25f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn,0), girth + 1.75f, 1, this.leaf, false);
		}
	}
}
