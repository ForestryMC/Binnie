package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenBox extends WorldGenTree {
    public WorldGenBox(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float bottom = 0.0f;
        float width = 1.5f;
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(3, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
