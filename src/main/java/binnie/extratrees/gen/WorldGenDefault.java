package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.WorldGenBlockType;

public class WorldGenDefault extends WorldGenTree {
	public WorldGenDefault(final ITreeGenData tree) {
		super(tree);
	}

	@Override
	public void generate() {
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height + 1;
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
	}

	@Override
	public void preGenerate() {
		this.height = this.determineHeight(5, 2);
		this.girth = this.determineGirth(this.treeGen.getGirth());
	}

	@Override
	public WorldGenBlockType getWood() {
		return new BlockTypeLog(this.treeGen);
	}
}
