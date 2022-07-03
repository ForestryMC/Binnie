package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenBalsamFir extends WorldGenTree {
    public WorldGenBalsamFir(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float bottom = 1.0f;
        float width = height / randBetween(2.0f, 2.5f);
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
        height = determineHeight(6, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
