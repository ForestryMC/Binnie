package binnie.extratrees.gen;

import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.WorldGenBlockType;
import forestry.api.world.ITreeGenData;

public class WorldGenDefault extends BinnieWorldGenTree {
	public WorldGenDefault(final ITreeGenData tree) {
		super(tree,5,2);
	}

	@Override
	protected void generateLeaves() {
		int leafSpawn = this.height + 1;
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 2.9f, 1, this.leaf, false);
	}

	@Override
	public WorldGenBlockType getWood() {
		return new BlockTypeLog(this.treeGen);
	}
}
