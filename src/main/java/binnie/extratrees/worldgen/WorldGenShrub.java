package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

public class WorldGenShrub extends WorldGenTree {
    public WorldGenShrub(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        float leafSpawn = height;
        float width = height * randBetween(0.7f, 0.9f);
        if (width < 1.5f) {
            width = 1.5f;
        }

        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.8f * width, 1, leaf, false);
        while (leafSpawn >= 0.0f) {
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        minHeight = 1;
        height = determineHeight(1, 1);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }

    @Override
    public BlockType getLeaf() {
        return new BlockTypeLeaf(false);
    }
}
