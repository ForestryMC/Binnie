package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenCoconut extends WorldGenTree {
    public WorldGenCoconut(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        float leafSpawn = height;
        float width = height * randBetween(0.35f, 0.4f);
        if (width < 1.2) {
            width = 1.55f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.6f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(6, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
