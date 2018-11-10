package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.modules.ModuleCore;

public class WorldGenHiveWater extends WorldGenHive {

	public WorldGenHiveWater(int rate) {
		super(rate);
	}

	@Override
	public boolean generate(final World world, final Random random, final BlockPos blockPos) {
		final Block block = world.getBlockState(blockPos).getBlock();
		if (block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
			return false;
		}
		final Material materialBlockBelow = world.getBlockState(blockPos.down()).getMaterial();
		if (materialBlockBelow == Material.SAND ||
			materialBlockBelow == Material.CLAY ||
			materialBlockBelow == Material.GROUND ||
			materialBlockBelow == Material.ROCK) {
			world.setBlockState(blockPos, ModuleCore.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, EnumHiveType.WATER));
		}
		return true;
	}
}
