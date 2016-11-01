package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenBanana extends WorldGenTree {
	public WorldGenBanana(final ITree tree) {
		super(tree);
	}

	@Override
	public void generate() {
		this.bushiness = 0.9f;
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height + 1;
		final float width = this.height / this.randBetween(1.8f, 2.0f);
		final int bottom = this.randBetween(3, 4);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, this.leaf, false);
	}

	@Override
	public void preGenerate() {
		this.height = this.determineHeight(6, 1);
		this.girth = this.determineGirth(this.treeGen.getGirth());
	}

}
