package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import net.minecraftforge.fml.common.IWorldGenerator;

import binnie.extrabees.utils.config.ConfigurationMain;

public class ExtraBeesWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(final Random rand, int chunkX, int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
		chunkX <<= 4;
		chunkZ <<= 4;
		for (int i = 0; i < ConfigurationMain.waterHiveRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveWater().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		for (int i = 0; i < ConfigurationMain.rockHiveRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveRock().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
		for (int i = 0; i < ConfigurationMain.netherHiveRate; ++i) {
			final int randPosX = chunkX + rand.nextInt(16);
			final int randPosY = rand.nextInt(50) + 20;
			final int randPosZ = chunkZ + rand.nextInt(16);
			new WorldGenHiveNether().generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}
}
