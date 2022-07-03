package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenPear extends WorldGenTree {
    public WorldGenPear(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float bottom = randBetween(1, 2);
        float width = height * randBetween(0.25f, 0.3f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(3, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
