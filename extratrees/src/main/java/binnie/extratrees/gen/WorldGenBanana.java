package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenBanana extends BinnieWorldGenTree {
	public WorldGenBanana(ITreeGenData tree) {
		super(tree, 6, 1);
	}

	@Override
	protected void generateLeaves() {
		int leafSpawn = this.height + 1;

		this.generateCylinder(new Vector(0, leafSpawn--, 0), this.girth, 1, this.leaf, false);
		this.generateCylinder(new Vector(0, leafSpawn--, 0), this.girth + 1.5f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0, leafSpawn, 0), this.girth + 1, 1, this.leaf, false);
	}
}
