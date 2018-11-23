package binnie.extratrees.gen;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.mojang.authlib.GameProfile;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.tiles.TileTreeContainer;

import binnie.core.util.TileUtil;
import binnie.extratrees.worldgen.BlockType;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.BlockTypeVoid;
import binnie.extratrees.worldgen.WorldGenBlockType;

public class WorldGenTree extends WorldGenerator {
	protected final ITreeGenData treeGen;
	protected World world;
	protected Random rand;
	protected int startX;
	protected int startY;
	protected int startZ;
	protected int girth;
	protected int height;
	protected final int minHeight;
	protected final int maxHeight;
	protected boolean spawnPods;
	protected final int minPodHeight;
	protected WorldGenBlockType leaf;
	protected WorldGenBlockType wood;
	protected final WorldGenBlockType vine;
	protected final WorldGenBlockType air;
	protected float bushiness;

	public WorldGenTree(ITreeGenData tree) {
		this.minHeight = 3;
		this.maxHeight = 80;
		this.spawnPods = false;
		this.minPodHeight = 3;
		this.vine = new BlockType(Blocks.VINE.getDefaultState());
		this.air = new BlockTypeVoid();
		this.bushiness = 0.0f;
		this.spawnPods = tree.allowsFruitBlocks();
		this.treeGen = tree;
	}

	protected int randBetween(int a, int b) {
		return a + this.rand.nextInt(b - a);
	}

	protected float randBetween(float a, float b) {
		return a + this.rand.nextFloat() * (b - a);
	}

	public void generate() {
		this.generateTreeTrunk(this.height, this.girth);
		int leafSpawn = this.height + 1;
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.0f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 1.5f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), 2.9f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), 2.9f, 1, this.leaf, false);
	}

	public boolean canGrow() {
		return this.treeGen.canGrow(this.world, new BlockPos(this.startX, this.startY, this.startZ), this.treeGen.getGirth(), this.height) != null;
	}

	public void preGenerate() {
		this.height = this.determineHeight(5, 3);
		this.girth = this.determineGirth(this.treeGen.getGirth());
	}

	protected int determineGirth(int base) {
		return base;
	}

	protected int modifyByHeight(int val, int min, int max) {
		int determined = Math.round(val * this.treeGen.getHeightModifier());
		return (determined < min) ? min : ((determined > max) ? max : determined);
	}

	protected int determineHeight(int required, int variation) {
		int determined = Math.round((required + this.rand.nextInt(variation)) * this.treeGen.getHeightModifier());
		return (determined < this.minHeight) ? this.minHeight : ((determined > this.maxHeight) ? this.maxHeight : determined);
	}

	public WorldGenBlockType getLeaf() {
		return leaf != null ? leaf : (leaf = new BlockTypeLeaf(null));
	}

	public WorldGenBlockType getWood() {
		return new BlockTypeLog(treeGen);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		this.leaf = new BlockTypeLeaf(getOwner(worldIn, position));
		return generate(worldIn, rand, position.getX(), position.getY(), position.getZ(), false);
	}

	@Nullable
	private static GameProfile getOwner(World world, BlockPos pos) {
		TileTreeContainer tile = TileUtil.getTile(world, pos, TileTreeContainer.class);
		if (tile == null) {
			return null;
		}
		return tile.getOwnerHandler().getOwner();
	}

	public final boolean generate(World world, Random random, int x, int y, int z, boolean force) {
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

	protected void generateTreeTrunk(int height, int width) {
		this.generateTreeTrunk(height, width, 0.0f);
	}

	protected void generateTreeTrunk(int height, int width, float vines) {
		int offset = (width - 1) / 2;
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
						treeGen.trySpawnFruitBlock(world, world.rand, new BlockPos(startX + x2 + 1, startY + y2, startZ + z));
						treeGen.trySpawnFruitBlock(world, world.rand, new BlockPos(startX + x2 - 1, startY + y2, startZ + z));
						treeGen.trySpawnFruitBlock(world, world.rand, new BlockPos(startX + x2, startY + y2, startZ + z + 1));
						treeGen.trySpawnFruitBlock(world, world.rand, new BlockPos(startX + x2, startY + y2, startZ + z - 1));
					}
				}
			}
		}
	}

	protected void generateSupportStems(int height, int girth, float chance, float maxHeight) {
		for (int offset = 1, x = -offset; x < girth + offset; ++x) {
			for (int z = -offset; z < girth + offset; ++z) {
				if (x != -offset || z != -offset) {
					if (x != girth + offset || z != girth + offset) {
						if (x != -offset || z != girth + offset) {
							if (x != girth + offset || z != -offset) {
								int stemHeight = this.rand.nextInt(Math.round(height * maxHeight));
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

	protected final void clearBlock(int x, int y, int z) {
		this.air.setBlock(this.world, this.treeGen, new BlockPos(this.startX + x, this.startY + y, this.startZ + z));
	}

	protected final void addBlock(int x, int y, int z, WorldGenBlockType type, boolean doReplace) {
		if (doReplace || this.world.isAirBlock(new BlockPos(this.startX + x, this.startY + y, this.startZ + z))) {
			type.setBlock(this.world, this.treeGen, new BlockPos(this.startX + x, this.startY + y, this.startZ + z));
		}
	}

	protected final void addWood(int x, int y, int z, boolean doReplace) {
		this.addBlock(x, y, z, this.wood, doReplace);
	}

	protected final void addLeaf(int x, int y, int z, boolean doReplace) {
		this.addBlock(x, y, z, this.leaf, doReplace);
	}

	protected final void addVine(int x, int y, int z) {
		this.addBlock(x, y, z, this.vine, false);
	}

	protected final void generateCuboid(Vector start, Vector area, WorldGenBlockType block, boolean doReplace) {
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					this.addBlock(x, y, z, block, doReplace);
				}
			}
		}
	}

	protected final void generateCylinder(Vector center2, float radius, int height, WorldGenBlockType block, boolean doReplace) {
		float centerOffset = (this.girth - 1) / 2.0f;
		Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
		Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
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

	protected final void generateCircle(Vector center, float radius, int width, int height, WorldGenBlockType block, boolean doReplace) {
		this.generateCircle(center, radius, width, height, block, 1.0f, doReplace);
	}

	protected final void generateCircle(Vector center2, float radius, int width, int height, WorldGenBlockType block, float chance, boolean doReplace) {
		float centerOffset = (this.girth % 2 == 0) ? 0.5f : 0.0f;
		Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
		Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (this.rand.nextFloat() <= chance) {
						double distance = Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z));
						if (radius - width - 0.01 < distance && distance <= radius + 0.01) {
							this.addBlock(x, y, z, block, doReplace);
						}
					}
				}
			}
		}
	}

	protected final void generateSphere(Vector center2, int radius, WorldGenBlockType block, boolean doReplace) {
		float centerOffset = (this.girth - 1) / 2.0f;
		Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		Vector start = new Vector(center3.x - radius, center3.y - radius, center3.z - radius);
		Vector area = new Vector(radius * 2 + 1, radius * 2 + 1, radius * 2 + 1);
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
		final float x;
		final float y;
		final float z;

		public Vector(float f, float h, float g) {
			this.x = f;
			this.y = h;
			this.z = g;
		}

		public static double distance(Vector a, Vector b) {
			return Math.sqrt(Math.pow(a.x - b.x, 2.0) + Math.pow(a.y - b.y, 2.0) + Math.pow(a.z - b.z, 2.0));
		}
	}
}
