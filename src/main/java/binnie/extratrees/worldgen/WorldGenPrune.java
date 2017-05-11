package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenPrune extends WorldGenTree {
	public WorldGenPrune(ITreeGenData tree) {
		super(tree);
	}

	@Override
	public void generate() {
		generateTreeTrunk(height, girth);
		int leafSpawn = height;
		float width = height / randBetween(1.7f, 2.1f);
		int bottom = randBetween(2, 3);
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
		while (leafSpawn > bottom) {
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
		}
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, leaf, false);
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, leaf, false);
	}

	@Override
	public void preGenerate() {
		height = determineHeight(6, 2);
		girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
	}
}
