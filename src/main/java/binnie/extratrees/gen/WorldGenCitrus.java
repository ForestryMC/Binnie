// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.gen;

import forestry.api.world.ITreeGenData;

public class WorldGenCitrus extends WorldGenTree
{
	public WorldGenCitrus(final ITreeGenData tree) {
		super(tree);
	}

	@Override
	public void generate() {
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height;
		final float width = this.height / this.randBetween(1.1f, 1.5f);
		final int bottom = this.randBetween(1, 2);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
		while (leafSpawn > bottom) {
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
		}
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
	}

	@Override
	public void preGenerate() {
		this.minHeight = this.randBetween(2, 3);
		this.height = this.determineHeight(6, 1);
		this.girth = this.determineGirth(this.treeGen.getGirth(this.world, this.startX, this.startY, this.startZ));
	}
}
