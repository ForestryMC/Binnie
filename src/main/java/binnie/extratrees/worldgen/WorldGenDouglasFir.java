package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenDouglasFir extends WorldGenTree {
    public WorldGenDouglasFir(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height + 1;
        float patchyBottom = height / 2;
        float bottom = randBetween(3, 4);
        float width = height * randBetween(0.35f, 0.4f);

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);

        bushiness = 0.1f;
        while (leafSpawn > patchyBottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.9f, 1.1f) * width, 1, leaf, false);
        }

        bushiness = 0.5f;
        while (leafSpawn > bottom) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.7f, 1.0f) * width, 1, leaf, false);
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 0.3f * width, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(7, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
