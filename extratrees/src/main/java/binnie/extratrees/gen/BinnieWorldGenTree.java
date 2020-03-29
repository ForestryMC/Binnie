package binnie.extratrees.gen;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.event.terraingen.TerrainGen;

import forestry.api.world.ITreeGenData;
import forestry.arboriculture.tiles.TileTreeContainer;
import forestry.arboriculture.worldgen.WorldGenTree;
import forestry.core.tiles.TileUtil;

import binnie.extratrees.worldgen.BlockType;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.BlockTypeVoid;
import binnie.extratrees.worldgen.WorldGenBlockType;

public class BinnieWorldGenTree extends WorldGenTree {
	protected final ITreeGenData treeGen;
	protected World world;
	protected Random rand;
	protected BlockPos pos;
	protected final int minHeight;
	protected final int maxHeight;
	protected int requiredHeight;
	protected int heightVariation;
	protected boolean spawnPods;
	protected final int minPodHeight;
	protected WorldGenBlockType leaf;
	protected WorldGenBlockType wood;
	protected final WorldGenBlockType vine;
	protected final WorldGenBlockType air;
	protected float bushiness;

	public BinnieWorldGenTree(ITreeGenData tree, int requiredHeight, int variation) {
		super(tree, requiredHeight, variation);
		this.minHeight = 3;
		this.maxHeight = 80;
		this.requiredHeight = requiredHeight;
		this.heightVariation = variation;
		this.minPodHeight = 3;
		this.vine = new BlockType(Blocks.VINE.getDefaultState());
		this.air = new BlockTypeVoid();
		this.bushiness = 0.0f;
		this.spawnPods = tree.allowsFruitBlocks();
		this.treeGen = tree;
	}

	protected int randBetween(final int a, final int b) {
		return a + this.rand.nextInt(b - a);
	}

	protected float randBetween(final float a, final float b) {
		return a + this.rand.nextFloat() * (b - a);
	}

	public boolean canGrow() {
		return this.treeGen.canGrow(this.world, this.pos, this.treeGen.getGirth(), this.height) != null;
	}

	protected int determineGirth(final int base) {
		return base;
	}

	protected int modifyByHeight(final int val, final int min, final int max) {
		final int determined = Math.round(val * this.treeGen.getHeightModifier());
		return (determined < min) ? min : (Math.min(determined, max));
	}

	protected int determineHeight(final int required, final int variation) {
		final int determined = Math.round((required + this.rand.nextInt(variation)) * this.treeGen.getHeightModifier());
		return (determined < this.minHeight) ? this.minHeight : (Math.min(determined, this.maxHeight));
	}

	public WorldGenBlockType getLeaf() {
		return leaf != null ? leaf : (leaf = new BlockTypeLeaf(null));
	}

	public WorldGenBlockType getLeaf(GameProfile owner)
	{
		return leaf = new BlockTypeLeaf(owner);
	}

	public WorldGenBlockType getWood() {
		return new BlockTypeLog(treeGen);
	}

	@Override
	public final boolean generate(World world, Random random, BlockPos pos, boolean force) {
		if (!TerrainGen.saplingGrowTree(world, rand, pos)) {
			return false;
		}

		GameProfile owner = getOwner(world, pos);
		this.world = world;
		this.rand = random;
		this.leaf = this.getLeaf(owner);
		this.wood = this.getWood();
		this.girth = treeGen.getGirth();
		this.height = determineHeight(requiredHeight, heightVariation);
		BlockPos genPos;
		if (force) {
			genPos = pos;
		} else {
			genPos = getValidGrowthPos(world, pos);
		}

		if (genPos == null || (!force && !this.canGrow())) {
			return false;
		}

		this.pos = genPos;

		if (genPos == null || (!force && !this.canGrow())) {
			return false;
		}

		this.preGenerate(world, random, this.pos);
    
		generateTrunk();
		generateLeaves();
		if(tree.allowsFruitBlocks()) {
			generateExtras();
		}
		return true;
	}

	private void generateTrunk() {
		for(int ty = 0; ty < this.height; ++ty) {
			for(int tx = 0; tx < girth; ++tx) {
				for(int tz = 0; tz < girth; ++tz) {
					this.addBlock(pos.add(tx, this.height - ty - 1, tz), wood, true);
				}
			}
		}
	}

	@Nullable
	private static GameProfile getOwner(World world, BlockPos pos) {
		TileTreeContainer tile = TileUtil.getTile(world, pos, TileTreeContainer.class);
		if (tile == null) {
			return null;
		}
		return tile.getOwnerHandler().getOwner();
	}

	protected void generateLeaves()
	{
		float leafSpawn = this.height;
		float width = this.height * this.randBetween(0.35f, 0.4f);
		if (width < 1.2) {
			width = 1.55f;
		}
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width - 1.0f, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn--, 0.0f), width, 1, this.leaf, false);
		this.generateCylinder(new Vector(0.0f, leafSpawn, 0.0f), width - 0.5f, 1, this.leaf, false);
	}

	protected void generateExtras()
	{
		for(int b = this.minPodHeight; b < this.height; ++b) {
			for(EnumFacing face : EnumFacing.HORIZONTALS) {
				BlockPos podPos = this.pos.add(face.getDirectionVec()).add(0,b,0);
				if(this.world.isAirBlock(podPos)) {
					this.treeGen.trySpawnFruitBlock(this.world, this.rand, this.pos.add(face.getDirectionVec()).add(0, b, 0));
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
										this.addWood(new BlockPos(x,i,z), true);
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
		this.air.setBlock(this.world, this.treeGen, this.pos, this.rand);
	}

	protected final void addBlock(BlockPos pos, final WorldGenBlockType type, final boolean doReplace) {
		if (doReplace || this.world.isAirBlock(pos)) {
			type.setBlock(this.world, this.treeGen, pos, this.rand);
		}
	}

	protected final void addWood(BlockPos pos, final boolean doReplace) {
		this.addBlock(pos, this.wood, doReplace);
	}

	protected final void addLeaf(BlockPos pos, final boolean doReplace) {
		this.addBlock(pos, this.leaf, doReplace);
	}

	protected final void addVine(BlockPos pos) {
		this.addBlock(pos, this.vine, false);
	}

	protected final void generateCuboid(final Vector start, final Vector area, final WorldGenBlockType block, final boolean doReplace) {
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					this.addBlock(pos, block, doReplace);
				}
			}
		}
	}

	protected final void generateCylinder(final Vector center2, final float radius, final int height, final WorldGenBlockType block, final boolean doReplace) {
		final float centerOffset = (this.girth - 1) / 2.0f;
		final Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		final Vector start = new Vector(center3.x - radius, center3.y, center3.z - radius);
		final Vector area = new Vector(radius * 2.0f + 1.0f, height, radius * 2.0f + 1.0f);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) <= radius + 0.01) {
						if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, y, center3.z)) < radius - 0.5f || this.rand.nextFloat() >= this.bushiness) {
							this.addBlock(this.pos.add(x,y,z), block, doReplace);
						}
					}
				}
			}
		}
	}

	protected final void generateCircle(final Vector center, final float radius, final int width, final int height, final WorldGenBlockType block, final boolean doReplace) {
		this.generateCircle(center, radius, width, height, block, 1.0f, doReplace);
	}

	protected final void generateCircle(final Vector center2, final float radius, final int width, final int height, final WorldGenBlockType block, final float chance, final boolean doReplace) {
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
							this.addBlock(new BlockPos(x,y,z), block, doReplace);
						}
					}
				}
			}
		}
	}

	protected final void generateSphere(final Vector center2, final int radius, final WorldGenBlockType block, final boolean doReplace) {
		final float centerOffset = (this.girth - 1) / 2.0f;
		final Vector center3 = new Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
		final Vector start = new Vector(center3.x - radius, center3.y - radius, center3.z - radius);
		final Vector area = new Vector(radius * 2 + 1, radius * 2 + 1, radius * 2 + 1);
		for (int x = (int) start.x; x < (int) start.x + area.x; ++x) {
			for (int y = (int) start.y; y < (int) start.y + area.y; ++y) {
				for (int z = (int) start.z; z < (int) start.z + area.z; ++z) {
					if (Vector.distance(new Vector(x, y, z), new Vector(center3.x, center3.y, center3.z)) <= radius + 0.01) {
						this.addBlock(new BlockPos(x,y,z), block, doReplace);
					}
				}
			}
		}
	}

	static class Vector {
		final float x;
		final float y;
		final float z;

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
