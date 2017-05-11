package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenCitrus extends WorldGenTree {
	public WorldGenCitrus(ITreeGenData tree) {
		super(tree);
	}

	@Override
	public void generate() {
		generateTreeTrunk(height, girth);
		int leafSpawn = height;
		float width = height / randBetween(1.1f, 1.5f);
		int bottom = randBetween(1, 2);
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
		while (leafSpawn > bottom) {
			generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
		}
		generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
	}

	@Override
	public void preGenerate() {
		minHeight = randBetween(2, 3);
		height = determineHeight(6, 1);
		girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
	}
}
