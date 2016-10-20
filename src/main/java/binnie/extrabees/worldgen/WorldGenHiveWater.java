// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.world.biome.BiomeGenBase;
import binnie.extrabees.ExtraBees;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHiveWater extends WorldGenerator
{
	@Override
	public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
		final BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
		final int i2 = i + random.nextInt(8) - random.nextInt(8);
		final int j2 = j + random.nextInt(4) - random.nextInt(4);
		final int k2 = k + random.nextInt(8) - random.nextInt(8);
		if (world.getBlock(i2, j2, k2) != Blocks.water && world.getBlock(i2, j2, k2) != Blocks.water) {
			return false;
		}
		if (world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.sand || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.clay || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.ground || world.getBlock(i2, j2 - 1, k2).getMaterial() == Material.rock) {
			world.setBlock(i2, j2, k2, ExtraBees.hive, 0, 0);
		}
		return true;
	}
}
