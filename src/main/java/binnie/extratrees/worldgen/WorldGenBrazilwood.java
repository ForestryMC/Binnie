package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenBrazilwood extends WorldGenTree {
    public WorldGenBrazilwood(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float bottom = 1.0f;
        float width = height * randBetween(0.15f, 0.2f);
        if (width < 2.0f) {
            width = 2.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.8f, 1, leaf, false);
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(4, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
