package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenBanana extends WorldGenTree {
    public WorldGenBanana(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        bushiness = 0.9f;
        generateTreeTrunk(height, girth);
        int leafSpawn = height + 1;
        float width = height / randBetween(1.8f, 2.0f);

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.9f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
