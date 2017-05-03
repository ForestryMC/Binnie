package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

// TODO unused class?
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
		minHeight = 4;
		maxHeight = 80;
		spawnPods = false;
		minPodHeight = 3;
	}

	protected int randBetween(int a, int b) {
		return a + rand.nextInt(b - a);
	}

	protected float randBetween(float a, float b) {
		return a + rand.nextFloat() * (b - a);
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		this.world = world;
		startX = x;
		startY = y;
		startZ = z;
		girth = tree.getGirth(world, x, y, z);
		height = (int) (randBetween(getHeight()[0], getHeight()[1]) * tree.getGenome().getHeight());

		if (tree.canGrow(world, x, y, z, girth, height)) {
			generate();
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

		public Vector(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
