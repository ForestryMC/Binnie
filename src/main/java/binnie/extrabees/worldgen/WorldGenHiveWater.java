package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHive;
import binnie.extrabees.blocks.type.EnumHiveType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHiveWater extends WorldGenerator {

	@Override
	public boolean generate(final World world, final Random random, final BlockPos pos) {
		final int i2 = pos.getX() + random.nextInt(8) - random.nextInt(8);
		final int j2 = pos.getY() + random.nextInt(4) - random.nextInt(4);
		final int k2 = pos.getZ() + random.nextInt(8) - random.nextInt(8);
		final BlockPos blockPos = new BlockPos(i2, j2, k2);
		if (world.getBlockState(blockPos).getBlock() != Blocks.WATER && world.getBlockState(blockPos).getBlock() != Blocks.WATER) {
			return false;
		}
		if (world.getBlockState(blockPos.down()).getMaterial() == Material.SAND || world.getBlockState(blockPos.down()).getMaterial() == Material.CLAY || world.getBlockState(blockPos.down()).getMaterial() == Material.GROUND || world.getBlockState(blockPos.down()).getMaterial() == Material.ROCK) {
			world.setBlockState(blockPos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Water));
		}
		return true;
	}

}
