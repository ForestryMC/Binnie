package binnie.extratrees.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;

public class TreeGenBase {
	protected ITree tree;
	protected ITreeGenData treeGen;
	protected World world;
	protected Random rand;
	protected int startX;
	protected int startY;
	protected int startZ;
	protected int girth;
	protected int height;
	protected final int minHeight;
	protected final int maxHeight;
	protected final boolean spawnPods;
	protected final int minPodHeight;

	public TreeGenBase() {
		this.minHeight = 4;
		this.maxHeight = 80;
		this.spawnPods = false;
		this.minPodHeight = 3;
	}

	protected final int randBetween(final int a, final int b) {
		return a + this.rand.nextInt(b - a);
	}

	protected final float randBetween(final float a, final float b) {
		return a + this.rand.nextFloat() * (b - a);
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		this.world = worldIn;
		this.startX = position.getX();	
		this.startY = position.getY();
		this.startZ = position.getZ();
		this.girth = this.tree.getGirth();
		this.height = (int) (this.randBetween(this.getHeight()[0], this.getHeight()[1]) * this.tree.getGenome().getHeight());
		if (this.tree.canGrow(world, position, this.girth, this.height) != null) {
			this.generate();
			return true;
		}
		return false;
	}

	protected void generate() {
	}

	protected int[] getHeight() {
		return new int[]{5, 2};
	}
}
