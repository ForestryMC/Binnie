package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenCommonBeech extends WorldGenTree {
    public WorldGenCommonBeech(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = randBetween(2, 3);
        float width = height * randBetween(0.47f, 0.5f);

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);

        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.5f, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 1.0f, 1, leaf, false);
    }
}
