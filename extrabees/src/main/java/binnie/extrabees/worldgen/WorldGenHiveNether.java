package binnie.extrabees.worldgen;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.minecraftforge.common.BiomeDictionary;

import forestry.api.apiculture.hives.IHiveGen;

public class WorldGenHiveNether implements IHiveGen {

	public WorldGenHiveNether() {
	}

	private boolean embedInWall(final World world, final Block blockID, final BlockPos pos) {
		if (world.getBlockState(pos).getBlock() != blockID) {
			return false;
		}
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (facing.getAxis() == EnumFacing.Axis.Y && world.getBlockState(pos.up()).getBlock() != blockID) {
				return false;
			}
			if (world.isAirBlock(pos.offset(facing))) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	@Override
	public BlockPos getPosForHive(World world, int x, int z) {
		final Biome biome = world.getBiome(new BlockPos(x, 0, z));
		if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
			return null;
		}
		final BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));    //void world
		if (topPos.getY() == 0) {
			return null;
		}
		int maxHeight = world.getHeight();        //in case mods change nether height

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(new BlockPos(x, maxHeight, z));

		while (pos.getY() > 0) {
			pos.move(EnumFacing.DOWN);
			if (isValidLocation(world, pos)) {
				return pos;
			}
		}

		return null;
	}

	@Override
	public boolean isValidLocation(World world, BlockPos pos) {
		final Biome biome = world.getBiome(pos);
		if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
			return false;
		}
		return this.embedInWall(world, Blocks.NETHERRACK, pos);
	}

	@Override
	public boolean canReplace(IBlockState blockState, World world, BlockPos pos) {
		Block block = blockState.getBlock();
		return block.isReplaceable(world, pos) && !blockState.getMaterial().isLiquid();
	}
}
