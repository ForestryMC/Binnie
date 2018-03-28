package binnie.extrabees.worldgen;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.apiculture.hives.IHiveGen;

public class WorldGenHiveMarble implements IHiveGen {
	private final Set<Block> validBlocks = new HashSet<>();

	public WorldGenHiveMarble() {
		//TODO config option / more ore names
		OreDictionary.getOres("stoneMarble").stream().filter(s -> s.getItem() instanceof ItemBlock).map(s -> ((ItemBlock) s.getItem()).getBlock()).forEach(validBlocks::add);
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		//get to the ground
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));
		int worldHeight = topPos.getY();
		if (topPos.getY() == 0) {
			return null;
		}

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(topPos);

		for (int i = 0; i < 10; i++) {
			pos.setY(world.rand.nextInt(worldHeight));

			if (isValidLocation(world, pos)) {
				return pos;
			}
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
