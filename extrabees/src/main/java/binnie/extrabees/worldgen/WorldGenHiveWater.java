package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;

public class WorldGenHiveWater extends WorldGenHive {

	public WorldGenHiveWater(int rate) {
		super(rate);
	}

	@Override
	public boolean generate(final World world, final Random random, final BlockPos blockPos) {
		final IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() != Blocks.WATER && blockState.getBlock() != Blocks.FLOWING_WATER) {
			return false;
		}
		final Material materialBlockBelow = world.getBlockState(blockPos.down()).getMaterial();
		if (materialBlockBelow == Material.SAND || materialBlockBelow == Material.CLAY || materialBlockBelow == Material.GROUND || materialBlockBelow == Material.ROCK) {
			world.setBlockState(blockPos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, EnumHiveType.WATER));
		}
		return true;
	}
}
