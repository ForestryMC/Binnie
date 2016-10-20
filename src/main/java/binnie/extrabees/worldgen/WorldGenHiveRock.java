// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import binnie.extrabees.ExtraBees;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHiveRock extends WorldGenerator
{
	@Override
	public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
		final BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
		final Block block = world.getBlock(i, j, k);
		if (block == null) {
			return true;
		}
		if (block.isReplaceableOreGen(world, i, j, k, Blocks.stone)) {
			world.setBlock(i, j, k, ExtraBees.hive, 1, 0);
		}
		return true;
	}
}
