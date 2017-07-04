package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenShrub extends WorldGenTree {
	public WorldGenShrub(ITreeGenData tree) {
		super(tree);
	}

	@Override
	public void generate() {
		float leafSpawn = height;
		float width = height * randBetween(0.7f, 0.9f);
		if (width < 1.5f) {
			width = 1.5f;
		}

		float h = leafSpawn;
		leafSpawn = h - 1.0f;
		generateCylinder(new Vector(0.0f, h, 0.0f), 0.4f * width, 1, leaf, false);

		float h2 = leafSpawn;
		leafSpawn = h2 - 1.0f;
		generateCylinder(new Vector(0.0f, h2, 0.0f), 0.8f * width, 1, leaf, false);

		while (leafSpawn >= 0.0f) {
			float h3 = leafSpawn;
			leafSpawn = h3 - 1.0f;
			generateCylinder(new Vector(0.0f, h3, 0.0f), width, 1, leaf, false);
		}
	}

	@Override
	public void preGenerate() {
		minHeight = 1;
		height = determineHeight(1, 1);
		girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
	}
}
