package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenOrchardApple extends WorldGenTree {
    public WorldGenOrchardApple(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        int leafSpawn = height;
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);

        while (leafSpawn > 2) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.5f, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 2.0f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(3, 6);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
