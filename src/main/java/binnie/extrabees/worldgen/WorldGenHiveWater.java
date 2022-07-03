package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHiveWater extends WorldGenerator {
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
        int i2 = i + random.nextInt(8) - random.nextInt(8);
        int j2 = j + random.nextInt(4) - random.nextInt(4);
        int k2 = k + random.nextInt(8) - random.nextInt(8);
        if (world.getBlock(i2, j2, k2) != Blocks.water && world.getBlock(i2, j2, k2) != Blocks.water) {
            return false;
        }

        if (world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.sand
                || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.clay
                || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.ground
                || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.rock) {
            world.setBlock(i2, j2, k2, ExtraBees.hive, 0, 0);
        }
        return true;
    }
}
