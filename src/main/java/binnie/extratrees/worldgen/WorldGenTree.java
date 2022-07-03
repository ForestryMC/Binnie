package binnie.extratrees.worldgen;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.world.ITreeGenData;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenTree extends WorldGenerator {
    protected ITreeGenData treeGen;
    protected World world;
    protected Random rand;
    protected int startX;
    protected int startY;
    protected int startZ;
    protected int girth;
    protected int height;
    protected int minHeight;
    protected int maxHeight;
    protected boolean spawnPods;
    protected int minPodHeight;
    BlockType leaf;
    BlockType wood;
    BlockType vine;
    BlockType air;
    float bushiness;

    public WorldGenTree(ITreeGenData tree) {
        minHeight = 3;
        maxHeight = 80;
        spawnPods = false;
        minPodHeight = 3;
        vine = new BlockType(Blocks.vine, 0);
        air = new BlockTypeVoid();
        bushiness = 0.0f;
        spawnPods = tree.allowsFruitBlocks();
        treeGen = tree;
    }

    protected int randBetween(int a, int b) {
        return a + rand.nextInt(b - a);
    }

    protected float randBetween(float a, float b) {
        return a + rand.nextFloat() * (b - a);
    }

    public void generate() {
        generateTreeTrunk(height, girth);
        int leafSpawn = height + 1;
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, leaf, false);
        generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, leaf, false);
    }

    public boolean canGrow() {
        return treeGen.canGrow(world, startX, startY, startZ, treeGen.getGirth(world, startX, startY, startZ), height);
    }

    public void preGenerate() {
        height = determineHeight(5, 3);
        girth = determineGirth(treeGen.getGirth(world, startX, startY, startZ));
    }

    protected int determineGirth(int base) {
        return base;
    }

    protected int determineHeight(int required, int variation) {
        int determined = Math.round((required + rand.nextInt(variation)) * treeGen.getHeightModifier());
        return (determined < minHeight) ? minHeight : ((determined > maxHeight) ? maxHeight : determined);
    }

    public BlockType getLeaf() {
        return new BlockTypeLeaf(true);
    }

    public BlockType getWood() {
        return new BlockTypeLog(((ExtraTreeSpecies) treeGen.getGenome().getPrimary()).getLog());
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        return generate(world, random, x, y, z, false);
    }

    public boolean generate(World world, Random random, int x, int y, int z, boolean force) {
        this.world = world;
        rand = random;
        startX = x;
        startY = y;
        startZ = z;
        leaf = getLeaf();
        wood = getWood();
        preGenerate();
        if (!force && !canGrow()) {
            return false;
        }

        for (int x2 = 0; x2 < girth; ++x2) {
            for (int y2 = 0; y2 < girth; ++y2) {
                addBlock(x2, 0, y2, new BlockTypeVoid(), true);
            }
        }
        generate();
        return true;
    }

    protected void generateTreeTrunk(int height, int width) {
        generateTreeTrunk(height, width, 0.0f);
    }

    protected void generateTreeTrunk(int height, int width, float vines) {
        int offset = (width - 1) / 2;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < width; ++y) {
                for (int i = 0; i < height; ++i) {
                    addWood(x - offset, i, y - offset, true);
                    if (rand.nextFloat() < vines) {
                        addVine(x - offset - 1, i, y - offset);
                    }
                    if (rand.nextFloat() < vines) {
                        addVine(x - offset + 1, i, y - offset);
                    }
                    if (rand.nextFloat() < vines) {
                        addVine(x - offset, i, y - offset - 1);
                    }
                    if (rand.nextFloat() < vines) {
                        addVine(x - offset, i, y - offset + 1);
                    }
                }
            }
        }
        if (!spawnPods) {
            return;
        }
        for (int y2 = minPodHeight; y2 < height; ++y2) {
            for (int x2 = 0; x2 < girth; ++x2) {
                for (int z = 0; z < girth; ++z) {
                    if (x2 <= 0 || x2 >= girth || z <= 0 || z >= girth) {
                        treeGen.trySpawnFruitBlock(world, startX + x2 + 1, startY + y2, startZ + z);
                        treeGen.trySpawnFruitBlock(world, startX + x2 - 1, startY + y2, startZ + z);
                        treeGen.trySpawnFruitBlock(world, startX + x2, startY + y2, startZ + z + 1);
                        treeGen.trySpawnFruitBlock(world, startX + x2, startY + y2, startZ + z - 1);
                    }
                }
            }
        }
    }

    protected void addBlock(int x, int y, int z, BlockType type, boolean doReplace) {
        if (doReplace || world.isAirBlock(startX + x, startY + y, startZ + z)) {
            type.setBlock(world, treeGen, startX + x, startY + y, startZ + z);
        }
    }

    protected void addWood(int x, int y, int z, boolean doReplace) {
        addBlock(x, y, z, wood, doReplace);
    }

    protected void addVine(int x, int y, int z) {
        addBlock(x, y, z, vine, false);
    }

    protected void generateCylinder(Vector center2, float radius, int height, BlockType block, boolean doReplace) {
        float centerOffset = (girth - 1) / 2.0f;
        Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
        Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
        Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
        for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
            for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
                for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
                    if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) <= radius + 0.01) {
                        if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) < radius - 0.5f
                                || rand.nextFloat() >= bushiness) {
                            addBlock(x, y, z, block, doReplace);
                        }
                    }
                }
            }
        }
    }

    protected void generateSphere(Vector center2, int radius, BlockType block, boolean doReplace) {
        float centerOffset = (girth - 1) / 2.0f;
        Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
        Vector start = new Vector(center3.x - radius, center3.y - radius, center3.z - radius);
        Vector area = new Vector(radius * 2 + 1, radius * 2 + 1, radius * 2 + 1);
        for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
            for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
                for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
                    if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, center3.y, center3.z))
                            <= radius + 0.01) {
                        addBlock(x, y, z, block, doReplace);
                    }
                }
            }
        }
    }

    static class Vector {
        float x;
        float y;
        float z;

        public Vector(float f, float h, float g) {
            x = f;
            y = h;
            z = g;
        }

        public static double distance(Vector a, Vector b) {
            return Math.sqrt(Math.pow(a.x - b.x, 2.0) + Math.pow(a.y - b.y, 2.0) + Math.pow(a.z - b.z, 2.0));
        }
    }
}
