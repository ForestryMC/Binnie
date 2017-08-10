package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenPrune extends WorldGenTree {
	public WorldGenPrune(final ITree tree) {
		super(tree);
	}

	@Override
	public void generate() {
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height;
		final float width = this.height / this.randBetween(1.7f, 2.1f);
		final int bottom = this.randBetween(2, 3);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, this.leaf, false);
		while (leafSpawn > bottom) {
			this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
		}
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, this.leaf, false);
	}

	@Override
	public void preGenerate() {
		this.height = this.determineHeight(6, 2);
		this.girth = this.determineGirth(this.treeGen.getGirth());
	}
}
