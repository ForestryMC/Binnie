package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenCommonAsh extends WorldGenTree {
    public WorldGenCommonAsh(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float bottom = randBetween(2, 3);
        float width = height * randBetween(0.35f, 0.45f);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);

        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.95f, 1.05f) * width, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.7f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
