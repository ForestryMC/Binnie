package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenBeech {
	public static class CommonBeech extends BinnieWorldGenTree {
		public CommonBeech(ITreeGenData tree) {
			super(tree, 5, 3);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(rand, 2, 3);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth, 1, this.leaf, false);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
			while(leafSpawn > bottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 2.5f, 1, this.leaf, false);
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.85f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.5f, 1, this.leaf, false);
		}
	}

	public static class CopperBeech extends CommonBeech {
		public CopperBeech(ITreeGenData tree) {
			super(tree);
		}
	}
}
