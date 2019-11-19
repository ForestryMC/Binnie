package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenAsh {
	public static class CommonAsh extends BinnieWorldGenTree {
		public CommonAsh(ITreeGenData tree) {
			super(tree, 5, 2);
		}

		@Override
		protected void generateLeaves() {
			float leafSpawn = this.height + 1;
			float bottom = WorldGenUtils.randBetween(this.rand, 2, 3);
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.75f, 1, this.leaf, false);
			while(leafSpawn > bottom) {
				this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 1.25f, 1, this.leaf, false);
			}
			this.generateCylinder(new Vector(0, leafSpawn--,0), girth + 0.25f, 1, this.leaf, false);
		}
	}
}
