package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenRowan extends WorldGenTree {
    public WorldGenRowan(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        int leafSpawn = height + 1;
        float bottom = randBetween(2, 3);
        float width = height * randBetween(0.5f, 0.6f);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.5f * width, 1, leaf, false);

        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
        }
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, leaf, false);
    }
}
