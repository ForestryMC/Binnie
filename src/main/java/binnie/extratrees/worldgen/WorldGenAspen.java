package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenAspen extends WorldGenTree {
    public WorldGenAspen(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float bottom = randBetween(height / 2, height / 2 + 1) + 1;
        float width = height * randBetween(0.25f, 0.35f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.9f, 1.1f) * width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
