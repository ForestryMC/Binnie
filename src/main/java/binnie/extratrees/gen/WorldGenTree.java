package binnie.extratrees.gen;

import binnie.extratrees.worldgen.BlockType;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.BlockTypeVoid;
import forestry.api.world.ITreeGenData;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenTree extends WorldGenerator {
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

	protected int randBetween(final int a, final int b) {
		return a + this.rand.nextInt(b - a);
	}

	protected float randBetween(final float a, final float b) {
		return a + this.rand.nextFloat() * (b - a);
	}

	public void generate() {
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height + 1;
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
	}

	public boolean canGrow() {
		return this.treeGen.canGrow(this.world, new BlockPos(this.startX, this.startY, this.startZ), this.treeGen.getGirth(), this.height) != null;
	}

	public void preGenerate() {
		this.height = this.determineHeight(5, 3);
		this.girth = this.determineGirth(this.treeGen.getGirth());
	}

	protected int determineGirth(final int base) {
		return base;
	}

	protected int modifyByHeight(final int val, final int min, final int max) {
		final int determined = Math.round(val * this.treeGen.getHeightModifier());
		return (determined < min) ? min : ((determined > max) ? max : determined);
	}

	protected int determineHeight(final int required, final int variation) {
		final int determined = Math.round((required + this.rand.nextInt(variation)) * this.treeGen.getHeightModifier());
		return (determined < this.minHeight) ? this.minHeight : ((determined > this.maxHeight) ? this.maxHeight : determined);
	}

	public BlockType getLeaf() {
		return new BlockTypeLeaf();
	}

	public BlockType getWood() {
		return new BlockTypeLog(treeGen);
	}

	public WorldGenTree(ITreeGenData tree) {
		this.minHeight = 3;
		this.maxHeight = 80;
		this.spawnPods = false;
		this.minPodHeight = 3;
		this.vine = new BlockType(Blocks.VINE, 0);
		this.air = new BlockTypeVoid();
		this.bushiness = 0.0f;
		this.spawnPods = tree.allowsFruitBlocks();
		this.treeGen = tree;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		return generate(worldIn, rand, position.getX(), position.getY(), position.getZ(), false);
	}

	public final boolean generate(final World world, final Random random, final int x, final int y, final int z, final boolean force) {
		this.world = world;
		this.rand = random;
		this.startX = x;
		this.startY = y;
		this.startZ = z;
		this.leaf = this.getLeaf();
		this.wood = this.getWood();
		this.preGenerate();
		if (!force && !this.canGrow()) {
			return false;
		}
		final int offset = (this.girth - 1) / 2;
		for (int x2 = 0; x2 < this.girth; ++x2) {
			for (int y2 = 0; y2 < this.girth; ++y2) {
				this.addBlock(x2, 0, y2, new BlockTypeVoid(), true);
			}
		}
		this.generate();
		return true;
	}

	public final Vector getStartVector() {
		return new Vector(this.startX, this.startY, this.startZ);
	}

	protected void generateTreeTrunk(final int height, final int width) {
		this.generateTreeTrunk(height, width, 0.0f);
	}

	protected void generateTreeTrunk(final int height, final int width, final float vines) {
		final int offset = (width - 1) / 2;
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < width; ++y) {
				for (int i = 0; i < height; ++i) {
					this.addWood(x - offset, i, y - offset, true);
					if (this.rand.nextFloat() < vines) {
						this.addVine(x - offset - 1, i, y - offset);
					}
					if (this.rand.nextFloat() < vines) {
						this.addVine(x - offset + 1, i, y - offset);
					}
					if (this.rand.nextFloat() < vines) {
						this.addVine(x - offset, i, y - offset - 1);
					}
					if (this.rand.nextFloat() < vines) {
						this.addVine(x - offset, i, y - offset + 1);
					}
				}
			}
		}
		if (!this.spawnPods) {
			return;
		}
		for (int y2 = this.minPodHeight; y2 < height; ++y2) {
			for (int x2 = 0; x2 < this.girth; ++x2) {
				for (int z = 0; z < this.girth; ++z) {
					if (x2 <= 0 || x2 >= this.girth || z <= 0 || z >= this.girth) {
						//this.treeGen.trySpawnFruitBlock(this.world, this.startX + x2 + 1, this.startY + y2, this.startZ + z);
						//this.treeGen.trySpawnFruitBlock(this.world, this.startX + x2 - 1, this.startY + y2, this.startZ + z);
						//this.treeGen.trySpawnFruitBlock(this.world, this.startX + x2, this.startY + y2, this.startZ + z + 1);
						//this.treeGen.trySpawnFruitBlock(this.world, this.startX + x2, this.startY + y2, this.startZ + z - 1);
					}
				}
			}
		}
	}

	protected void generateSupportStems(final int height, final int girth, final float chance, final float maxHeight) {
		for (int offset = 1, x = -offset; x < girth + offset; ++x) {
			for (int z = -offset; z < girth + offset; ++z) {
				if (x != -offset || z != -offset) {
					if (x != girth + offset || z != girth + offset) {
						if (x != -offset || z != girth + offset) {
							if (x != girth + offset || z != -offset) {
								final int stemHeight = this.rand.nextInt(Math.round(height * maxHeight));
								if (this.rand.nextFloat() < chance) {
									for (int i = 0; i < stemHeight; ++i) {
										this.addWood(x, i, z, true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected final void clearBlock(final int x, final int y, final int z) {
		this.air.setBlock(this.world, this.treeGen, new BlockPos(this.startX + x, this.startY + y, this.startZ + z));
	}

	protected final void addBlock(final int x, final int y, final int z, final BlockType type, final boolean doReplace) {
		if (doReplace || this.world.isAirBlock(new BlockPos(this.startX + x, this.startY + y, this.startZ + z))) {
			type.setBlock(this.world, this.treeGen, new BlockPos(this.startX + x, this.startY + y, this.startZ + z));
		}
	}

	protected final void addWood(final int x, final int y, final int z, final boolean doReplace) {
		this.addBlock(x, y, z, this.wood, doReplace);
	}

	protected final void addLeaf(final int x, final int y, final int z, final boolean doReplace) {
		this.addBlock(x, y, z, this.leaf, doReplace);
	}

	protected final void addVine(final int x, final int y, final int z) {
		this.addBlock(x, y, z, this.vine, false);
	}

	protected final void generateCuboid(final Vector start, final Vector area, final BlockType block, final boolean doReplace) {
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					this.addBlock(x, y, z, block, doReplace);
				}
			}
		}
	}

	protected final void generateCylinder(final Vector center2, final float radius, final int height, final BlockType block, final boolean doReplace) {
		final float centerOffset = (this.girth - 1) / 2.0f;
		final Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		final Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
		final Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) <= radius + 0.01) {
						if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) < radius - 0.5f || this.rand.nextFloat() >= this.bushiness) {
							this.addBlock(x, y, z, block, doReplace);
						}
					}
				}
			}
		}
	}

	protected final void generateCircle(final Vector center, final float radius, final int width, final int height, final BlockType block, final boolean doReplace) {
		this.generateCircle(center, radius, width, height, block, 1.0f, doReplace);
	}

	protected final void generateCircle(final Vector center2, final float radius, final int width, final int height, final BlockType block, final float chance, final boolean doReplace) {
		final float centerOffset = (this.girth % 2 == 0) ? 0.5f : 0.0f;
		final Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		final Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
		final Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (this.rand.nextFloat() <= chance) {
						final double distance = Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z));
						if (radius - width - 0.01 < distance && distance <= radius + 0.01) {
							this.addBlock(x, y, z, block, doReplace);
						}
					}
				}
			}
		}
	}

	protected final void generateSphere(final Vector center2, final int radius, final BlockType block, final boolean doReplace) {
		final float centerOffset = (this.girth - 1) / 2.0f;
		final Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		final Vector start = new Vector(center3.x - radius, center3.y - radius, center3.z - radius);
		final Vector area = new Vector(radius * 2 + 1, radius * 2 + 1, radius * 2 + 1);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, center3.y, center3.z)) <= radius + 0.01) {
						this.addBlock(x, y, z, block, doReplace);
					}
				}
			}
		}
	}

	static class Vector {
		float x;
		float y;
		float z;

		public Vector(final float f, final float h, final float g) {
			this.x = f;
			this.y = h;
			this.z = g;
		}

		public static double distance(final Vector a, final Vector b) {
			return Math.sqrt(Math.pow(a.x - b.x, 2.0) + Math.pow(a.y - b.y, 2.0) + Math.pow(a.z - b.z, 2.0));
		}
	}
}
