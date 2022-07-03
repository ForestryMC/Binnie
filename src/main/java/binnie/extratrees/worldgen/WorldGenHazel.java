package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenHazel extends WorldGenTree {
    public WorldGenHazel(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = 3.0f;
        float width = height * randBetween(0.45f, 0.5f);
        if (width < 2.5f) {
            width = 2.5f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.3f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.2f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
