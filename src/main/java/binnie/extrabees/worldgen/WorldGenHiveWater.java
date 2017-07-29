package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHive;
import binnie.extrabees.blocks.type.EnumHiveType;

public class WorldGenHiveWater extends WorldGenHive {

	public WorldGenHiveWater(int rate) {
		super(rate);
	}

	@Override
	public boolean generate(final World world, final Random random, final BlockPos blockPos) {
		if (world.getBlockState(blockPos).getBlock() != Blocks.WATER && world.getBlockState(blockPos).getBlock() != Blocks.WATER) {
			return false;
		}
		if (world.getBlockState(blockPos.down()).getMaterial() == Material.SAND || world.getBlockState(blockPos.down()).getMaterial() == Material.CLAY || world.getBlockState(blockPos.down()).getMaterial() == Material.GROUND || world.getBlockState(blockPos.down()).getMaterial() == Material.ROCK) {
			world.setBlockState(blockPos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Water));
		}
		return true;
	}
}
