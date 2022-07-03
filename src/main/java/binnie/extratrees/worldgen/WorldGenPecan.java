package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenPecan extends WorldGenTree {
    public WorldGenPecan(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = 2.0f;
        float width = height * randBetween(0.6f, 0.65f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.3f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.6f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.6f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
