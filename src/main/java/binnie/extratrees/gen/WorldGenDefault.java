// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.gen;

import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockType;
import forestry.api.world.ITreeGenData;

public class WorldGenDefault extends WorldGenTree
{
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
		this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
	}

	@Override
	public BlockType getLeaf() {
		return new BlockTypeLeaf();
	}

	@Override
	public BlockType getWood() {
		return new BlockTypeLog(((ExtraTreeSpecies) this.treeGen.getGenome().getPrimary()).getLog());
	}
}
