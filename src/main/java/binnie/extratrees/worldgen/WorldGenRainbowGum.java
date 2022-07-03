package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenRainbowGum extends WorldGenTree {
    public WorldGenRainbowGum(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float bottom = randBetween(0.5f, 0.6f) * height;
        float width = height * randBetween(0.15f, 0.2f);
        if (width < 1.5f) {
            width = 1.5f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);

        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(7, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
