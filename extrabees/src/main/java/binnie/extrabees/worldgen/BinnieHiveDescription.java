package binnie.extrabees.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.tiles.TileHive;
import ic2.core.energy.grid.Tile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.minecraftforge.common.BiomeDictionary;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.hives.IHiveDescription;
import forestry.api.apiculture.hives.IHiveGen;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;

import binnie.extrabees.blocks.BlockExtraBeeHives;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import binnie.extrabees.modules.ModuleCore;
import binnie.extrabees.utils.config.ConfigurationMain;

public enum BinnieHiveDescription implements IHiveDescription {

	WATER(EnumHiveType.WATER, ConfigurationMain.getWaterHiveRate(), ExtraBeeDefinition.WATER, new WorldGenHiveWater()),
	MARBLE(EnumHiveType.MARBLE, ConfigurationMain.getMarbleHiveRate() * 10, ExtraBeeDefinition.MARBLE, new WorldGenHiveMarble()),
	ROCK(EnumHiveType.ROCK, ConfigurationMain.getRockHiveRate() * 3, ExtraBeeDefinition.ROCK, new WorldGenHiveRock()),
	NETHER(EnumHiveType.NETHER, ConfigurationMain.getNetherHiveRate() * 3, ExtraBeeDefinition.BASALT, new WorldGenHiveNether());

	private final EnumHiveType hiveType;
	private final float genChance;
	private final IBeeGenome genome;
	private final IHiveGen hiveGen;

	BinnieHiveDescription(EnumHiveType hiveType, float genChance, ExtraBeeDefinition beeDefinition, IHiveGen hiveGen) {
		this.hiveType = hiveType;
		this.genChance = genChance;
		this.genome = beeDefinition.getGenome();
		this.hiveGen = hiveGen;
	}

	@Override
	public IHiveGen getHiveGen() {
		return hiveGen;
	}

	@Override
	public IBlockState getBlockState() {
		return ModuleCore.hive.getDefaultState().withProperty(BlockExtraBeeHives.HIVE_TYPE, hiveType);
	}

	@Override
	public boolean isGoodBiome(Biome biome) {
		return hiveType != EnumHiveType.NETHER ||
				BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER);
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
