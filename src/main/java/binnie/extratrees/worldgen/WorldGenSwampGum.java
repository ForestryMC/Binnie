package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenSwampGum extends WorldGenTree {
    public WorldGenSwampGum(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float weakerBottm = randBetween(0.5f, 0.6f) * height;
        float bottom = randBetween(0.4f, 0.5f) * height;
        float width = height * randBetween(0.15f, 0.2f);
        if (width > 7.0f) {
            width = 7.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);

        bushiness = 0.3f;
        while (leafSpawn > weakerBottm) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.9f, 1.0f) * width, 1, leaf, false);
        }

        bushiness = 0.6f;
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.8f, 0.9f) * width, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, leaf, false);
        for (int i = 0; i < 5; ++i) {
            generateSphere(
                    new Vector(randBetween(-1, 1), leafSpawn--, randBetween(-1, 1)), randBetween(1, 2), leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(14, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
