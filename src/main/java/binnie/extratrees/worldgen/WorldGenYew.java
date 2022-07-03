package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenYew extends WorldGenTree {
    public WorldGenYew(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float bottom = randBetween(1, 2);
        float width = height * randBetween(0.7f, 0.75f);
        if (width > 7.0f) {
            width = 7.0f;
        }

        float coneHeight = leafSpawn - bottom;
        while (leafSpawn > bottom) {
            float radius = 1.0f - (leafSpawn - bottom) / coneHeight;
            radius *= (2.0f - radius) * width;
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), radius, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
