package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public class WorldGenHiveNether extends WorldGenHive {

	public WorldGenHiveNether(int rate) {
		super(rate);
	}

	@Override
	public boolean generate(final World world, final Random random, final BlockPos pos) {
		final Biome biome = world.getBiome(pos);
		if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
			return true;
		}
		if (this.embedInWall(world, Blocks.NETHERRACK, pos)) {
			world.setBlockState(pos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, EnumHiveType.NETHER));
		}
		return true;
	}

	public boolean embedInWall(final World world, final Block blockID, final BlockPos pos) {
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
}
