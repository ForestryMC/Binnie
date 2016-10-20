// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import binnie.extrabees.ExtraBees;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHiveNether extends WorldGenerator
{
	@Override
	public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
		final BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, k);
		if (!BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)) {
			return true;
		}
		if (this.embedInWall(world, Blocks.netherrack, i, j, k)) {
			world.setBlock(i, j, k, ExtraBees.hive, 2, 0);
		}
		return true;
	}

	public boolean embedInWall(final World world, final Block blockID, final int i, final int j, final int k) {
		return world.getBlock(i, j, k) == blockID && world.getBlock(i, j + 1, k) == blockID && world.getBlock(i, j - 1, k) == blockID && (world.isAirBlock(i + 1, j, k) || world.isAirBlock(i - 1, j, k) || world.isAirBlock(i, j, k + 1) || world.isAirBlock(i, j, k - 1));
	}
}
