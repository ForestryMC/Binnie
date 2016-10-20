package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public class WorldGenHiveNether extends WorldGenerator {
    @Override
    public boolean generate(final World world, final Random random, final BlockPos pos) {
        final Biome biome = world.getBiome(pos);
        if (!BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)) {
            return true;
        }
        if (this.embedInWall(world, Blocks.NETHERRACK, pos)) {
            world.setBlockState(pos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Nether));
        }
        return true;
    }

    public boolean embedInWall(final World world, final Block blockID, final BlockPos pos) {
        return world.getBlockState(pos).getBlock() == blockID && world.getBlockState(pos.up()).getBlock() == blockID && world.getBlockState(pos.down()).getBlock() == blockID && (world.isAirBlock(pos.add(1, 0, 0)) || world.isAirBlock(pos.add(-1, 0, 0)) || world.isAirBlock(pos.add(0, 0, 1)) || world.isAirBlock(pos.add(0, 0, -1)));
    }
}
