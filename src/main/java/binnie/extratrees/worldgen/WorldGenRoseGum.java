package binnie.extratrees.worldgen;

import binnie.extratrees.block.ILogType;
import forestry.api.world.ITreeGenData;

public class WorldGenRoseGum extends WorldGenTree {
    public WorldGenRoseGum(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        int offset = (girth - 1) / 2;
        for (int x = 0; x < girth; ++x) {
            for (int y = 0; y < girth; ++y) {
                for (int i = 0; i < 2; ++i) {
                    addBlock(x - offset, i, y - offset, new BlockTypeLog(ILogType.ExtraTreeLog.Eucalyptus2), true);
                }
            }
        }

        float leafSpawn = height + 2;
        float bottom = randBetween(0.4f, 0.5f) * height;
        float width = height * randBetween(0.05f, 0.1f);
        if (width < 1.5f) {
            width = 1.5f;
        }

        bushiness = 0.5f;
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.4f * width, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 0.7f * width, 1, leaf, false);

        while (leafSpawn > bottom) {
            bushiness = 0.1f;
            generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), randBetween(0.9f, 1.1f) * width, 1, leaf, false);
        }
    }

    @Override
    public void preGenerate() {
        height = determineHeight(9, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }
}
