package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenSycamore extends WorldGenTree {
    public WorldGenSycamore(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float bottom = randBetween(2, 3);
        float width = height * randBetween(0.35f, 0.4f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.3f, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.6f, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.8f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
