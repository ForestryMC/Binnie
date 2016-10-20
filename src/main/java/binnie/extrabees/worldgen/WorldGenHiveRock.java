package binnie.extrabees.worldgen;


import binnie.extrabees.ExtraBees;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHiveRock extends WorldGenerator {
    @Override
    public boolean generate(final World world, final Random random, final BlockPos pos) {
        final Biome biome = world.getBiome(pos);
        final Block block = world.getBlockState(pos).getBlock();
        if (block == null) {
            return true;
        }
        if (block.isReplaceableOreGen(world.getBlockState(pos), world, pos, BlockStateMatcher.forBlock(Blocks.STONE))) {
            world.setBlockState(pos, ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHive.hiveType, EnumHiveType.Rock));
        }
        return true;
    }
}
