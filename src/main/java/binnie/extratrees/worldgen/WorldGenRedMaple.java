package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenRedMaple extends WorldGenTree {
    public WorldGenRedMaple(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 2;
        float bottom = randBetween(1, 2);
        float bottom2 = (height + bottom) / 2.0f;
        float width = 2.0f;

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);

        while (leafSpawn > bottom2) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, leaf, false);
        }

        width += 0.6;
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 0.4f, 1, leaf, false);
        }

        if (leafSpawn == bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.6f, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
