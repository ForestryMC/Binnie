package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TreeGenBase extends WorldGenerator {
	protected ITree tree;
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


	@Override
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

	private static class Vector {
		int x;
		int y;
		int z;

		public Vector(final int x, final int y, final int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
