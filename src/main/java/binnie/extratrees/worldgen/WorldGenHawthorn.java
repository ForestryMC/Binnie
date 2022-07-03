package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenHawthorn extends WorldGenTree {
    public WorldGenHawthorn(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = randBetween(1, 2);
        float width = height * randBetween(0.4f, 0.45f);
        if (width < 1.5f) {
            width = 1.5f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(4, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
