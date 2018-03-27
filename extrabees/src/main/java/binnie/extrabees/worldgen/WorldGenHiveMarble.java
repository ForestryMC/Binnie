package binnie.extrabees.worldgen;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.apiculture.hives.IHiveGen;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;

public class WorldGenHiveMarble implements IHiveGen {
	private final Set<Block> validBlocks = new HashSet<>();

	public WorldGenHiveMarble() {
		//TODO config option / more ore names
		OreDictionary.getOres("stoneMarble").stream().filter(s -> s.getItem() instanceof ItemBlock).map(s -> ((ItemBlock) s.getItem()).getBlock()).forEach(validBlocks::add);
	}

	public boolean generate(World world, Random rand, BlockPos position) {
		Block blockAtPos = world.getBlockState(position).getBlock();
		if (world.isAirBlock(position) || !validBlocks.contains(blockAtPos)) {
			return false;
		}

		//generate when one face is different from marble
		int otherFace = 0;
		for (EnumFacing face : EnumFacing.values()) {
			if (!world.getBlockState(position.offset(face)).getBlock().equals(blockAtPos)) {
				otherFace++;
				if (otherFace > 1) {
					return true;
				}
			}
		}

		world.setBlockState(position, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, EnumHiveType.MARBLE));

		return true;
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		//get to the ground
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));
		if (topPos.getY() == 0) {
			return null;
		}

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);

		while (pos.getY() > 0) {
			if (isValidLocation(world, pos)) {
				return pos;
			}
			pos.add(0, -1, 0);
		}
		return null;
	}

	@Override
	public boolean isValidLocation(World world, BlockPos pos) {
		Block blockAtPos = world.getBlockState(pos).getBlock();
		if (world.isAirBlock(pos) || !validBlocks.contains(blockAtPos)) {
			return false;
		}

		//generate when one face is different from marble
		int otherFace = 0;
		for (EnumFacing face : EnumFacing.values()) {
			if (!world.getBlockState(pos.offset(face)).getBlock().equals(blockAtPos)) {
				otherFace++;
			}
		}
		return otherFace == 1;
	}

	@Override
	public boolean canReplace(IBlockState blockState, World world, BlockPos pos) {
		Block block = blockState.getBlock();
		return block.isReplaceable(world, pos) && !blockState.getMaterial().isLiquid();
	}
}
