package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenIroko extends WorldGenTree {
    public WorldGenIroko(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float width = height * randBetween(0.45f, 0.5f);
        if (width < 2.5f) {
            width = 2.5f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.25f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.5f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width * 0.7f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 2.0f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
