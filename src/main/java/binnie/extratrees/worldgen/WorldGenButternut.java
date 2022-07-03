package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenButternut extends WorldGenTree {
    public WorldGenButternut(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        int leafSpawn = height + 1;
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.0f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 3.5f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 4.5f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, leaf, false);

        while (leafSpawn > 3) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 5.0f, 1, leaf, false);
        }
        if (rand.nextBoolean()) {
            generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 4.0f, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
