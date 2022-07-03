package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenMonkeyPuzzle extends WorldGenTree {
    public WorldGenMonkeyPuzzle(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float width = height * randBetween(0.4f, 0.45f);
        if (width > 7.0f) {
            width = 7.0f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.35f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.55f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.75f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.9f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.5f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(9, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
