package binnie.extrabees.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.hives.IHiveDescription;
import forestry.api.apiculture.hives.IHiveGen;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeeDefinition;

public enum BinnieHiveDescription implements IHiveDescription {
	WATER(EnumHiveType.WATER, 2.0f, ExtraBeeDefinition.WATER, new WorldGenHiveWater()),
	MARBLE(EnumHiveType.MARBLE, 2.0f, ExtraBeeDefinition.MARBLE, new WorldGenHiveMarble()),
	ROCK(EnumHiveType.ROCK, 2.0f, ExtraBeeDefinition.ROCK, new WorldGenHiveRock()),
	NETHER(EnumHiveType.NETHER, 2.0f, ExtraBeeDefinition.BASALT, new WorldGenHiveNether());


	private final IBlockState hiveState;
	private final EnumHiveType hiveType;
	private final float genChance;
	private final IBeeGenome genome;
	private final IHiveGen hiveGen;

	BinnieHiveDescription(EnumHiveType hiveType, float genChance, ExtraBeeDefinition beeDefinition, IHiveGen hiveGen) {
		this.hiveType = hiveType;
		this.hiveState = getState(hiveType);
		this.genChance = genChance;
		this.genome = beeDefinition.getGenome();
		this.hiveGen = hiveGen;
	}

	private static IBlockState getState(EnumHiveType hiveType) {
		return ExtraBees.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, hiveType);
	}

	@Override
	public IHiveGen getHiveGen() {
		return hiveGen;
	}

	@Override
	public IBlockState getBlockState() {
		return hiveState;
	}

	@Override
	public boolean isGoodBiome(Biome biome) {
		return true;
	}

	@Override
	public boolean isGoodHumidity(EnumHumidity humidity) {
		EnumHumidity idealHumidity = genome.getPrimary().getHumidity();
		EnumTolerance humidityTolerance = genome.getToleranceHumid();
		return AlleleManager.climateHelper.isWithinLimits(humidity, idealHumidity, humidityTolerance);
	}

	@Override
	public boolean isGoodTemperature(EnumTemperature temperature) {
		EnumTemperature idealTemperature = genome.getPrimary().getTemperature();
		EnumTolerance temperatureTolerance = genome.getToleranceTemp();
		return AlleleManager.climateHelper.isWithinLimits(temperature, idealTemperature, temperatureTolerance);
	}

	@Override
	public float getGenChance() {
		return genChance;
	}

	public String getName() {
		return hiveType.getName();
	}

	@Override
	public void postGen(World world, Random random, BlockPos pos) {

	}

}