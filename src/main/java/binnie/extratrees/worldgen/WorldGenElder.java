package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenElder extends WorldGenTree {
    public WorldGenElder(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = 3.0f;
        float width = height * randBetween(0.55f, 0.6f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.0f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(4, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
