package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.modules.ModuleCore;

public class WorldGenHiveRock extends WorldGenHive {

	public WorldGenHiveRock(int rate) {
		super(rate);
	}

	@Override
	public boolean generate(final World world, final Random random, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		while (block.isReplaceableOreGen(blockState, world, pos, BlockStateMatcher.forBlock(Blocks.STONE)))
		{
			if (hasAirOnOneSide(world, pos)) {
				IBlockState hiveState = ModuleCore.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, EnumHiveType.ROCK);
				world.setBlockState(pos, hiveState);
				return true;
			}
			pos = pos.down();
			blockState = world.getBlockState(pos);
			block = blockState.getBlock();
		}
		return false;
	}

	public boolean hasAirOnOneSide(final World world, final BlockPos pos) {
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos sidePos = pos.offset(facing);
			if (world.isBlockLoaded(sidePos) && world.isAirBlock(sidePos)) {
				return true;
			}
		}
		return false;
	}
}
