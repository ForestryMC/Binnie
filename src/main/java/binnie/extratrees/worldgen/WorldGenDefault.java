package binnie.extratrees.worldgen;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.world.ITreeGenData;

public class WorldGenDefault extends WorldGenTree {
    public WorldGenDefault(ITreeGenData tree) {
        super(tree);
    }

    @Override
    public void generate() {
        generateTreeTrunk(height, girth);
        int leafSpawn = height + 1;
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, leaf, false);
    }

    @Override
    public void preGenerate() {
        height = determineHeight(5, 2);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }

    @Override
    public BlockType getWood() {
        return new BlockTypeLog(((ExtraTreeSpecies) treeGen.getGenome().getPrimary()).getLog());
    }
}
