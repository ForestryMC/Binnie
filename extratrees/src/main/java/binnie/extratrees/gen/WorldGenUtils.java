package binnie.extratrees.gen;

import forestry.arboriculture.worldgen.ITreeBlockType;
import forestry.core.utils.VectUtil;
import forestry.core.worldgen.WorldGenHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenUtils {

	public static int randBetween(Random rand, int a, int b) {
		return a + rand.nextInt(b - a);
	}

	public static float randBetween(Random rand, float a, float b) {
		return a + rand.nextFloat() * (b - a);
	}

	/**
	 * Uses centerPos and girth of a tree to calculate the center
	 */
	public static void generateCylinderFromTreeStartPos(World world, Random rand, ITreeBlockType block, BlockPos startPos, int girth, float radius, int height, float bushiness, WorldGenHelper.EnumReplaceMode replace) {
		generateCylinderFromPos(world, rand, block, startPos.add(girth / 2, 0, girth / 2), radius, height, bushiness, replace);
	}

	/**
	 * Center is the bottom middle of the cylinder
	 */
	public static void generateCylinderFromPos(World world, Random rand, ITreeBlockType block, BlockPos center, float radius, int height, float bushiness, WorldGenHelper.EnumReplaceMode replace) {
		BlockPos start = new BlockPos(center.getX() - radius, center.getY(), center.getZ() - radius);
		for (int x = 0; x < radius * 2 + 1; x++) {
			for (int y = height - 1; y >= 0; y--) { // generating top-down is faster for lighting calculations
				for (int z = 0; z < radius * 2 + 1; z++) {
					BlockPos position = start.add(x, y, z);
					Vec3i treeCenter = new Vec3i(center.getX(), position.getY(), center.getZ());
					if (position.distanceSq(treeCenter) <= radius * radius + 0.01) {
						if (position.distanceSq(treeCenter) < radius * radius - (0.5f * 0.5f) || rand.nextFloat() >= bushiness) {
							EnumFacing direction = VectUtil.direction(position, treeCenter);
							block.setDirection(direction);
							WorldGenHelper.addBlock(world, position, block, replace);
						}
					}
				}
			}
		}
	}
}
