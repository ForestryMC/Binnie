package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenSilverFir extends WorldGenTree {
    public WorldGenSilverFir(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float bottom = randBetween(2, 3);
        float width = height / randBetween(2.5f, 3.0f);
        if (width > 7.0f) {
            width = 7.0f;
        }

        float coneHeight = leafSpawn - bottom;
        while (leafSpawn > bottom) {
            float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
            radius *= width;
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.4f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(7, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
