package binnie.botany;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import forestry.api.climate.IClimateState;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ForestryAPI;

import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumFertiliserType;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.blocks.BlockPlant;
import binnie.botany.blocks.BlockSoil;
import binnie.botany.modules.ModuleGardening;
import binnie.core.util.OreDictionaryUtil;

public class GardeningManager implements IGardeningManager {
	private final Map<EnumFertiliserType, Map<ItemStack, Integer>> fertilisers = new LinkedHashMap<>();

	public GardeningManager() {
		for(EnumFertiliserType type : EnumFertiliserType.values()){
			fertilisers.put(type, new LinkedHashMap<>());
		}
	}

	@Override
	public boolean isSoil(Block block) {
		return block instanceof IBlockSoil;
	}

	@Override
	public boolean isSoil(Item item) {
		return item instanceof ItemBlock && isSoil(((ItemBlock) item).getBlock());
	}

	@Override
	public boolean isSoil(ItemStack item) {
		return isSoil(item.getItem());
	}

	@Override
	public EnumMoisture getNaturalMoisture(World world, BlockPos pos) {
		float moisture = getBiomeMoisture(world, pos);

		for (int offsetX = -1; offsetX < 2; ++offsetX) {
			for (int offsetY = -1; offsetY < 2; ++offsetY) {
				if (offsetX != 0 || offsetY != 0) {
					Block block = world.getBlockState(pos.add(offsetX, 0, offsetY)).getBlock();
					if (block == Blocks.SAND) {
						moisture -= 1.5;
					} else if (block == Blocks.WATER) {
						moisture += 1.5;
					}
				}
			}
		}

		if (world.isRainingAt(pos.up())) {
			moisture += 1.5;
		}

		for (int offsetX = -1; offsetX < 2; ++offsetX) {
			for (int offsetY = -1; offsetY < 2; ++offsetY) {
				if (offsetX != 0 || offsetY != 0) {
					Block block = world.getBlockState(pos.add(offsetX, 0, offsetY)).getBlock();
					if (block == Blocks.GRAVEL && moisture > 0.0f) {
						moisture *= 0.4f;
					}
				}
			}
		}
		return EnumMoisture.getFromValue(moisture);
	}

	@Override
	public EnumAcidity getNaturalPH(World world, BlockPos pos) {
		float acidity = getBiomePH(world, pos);
		return EnumAcidity.getFromValue(acidity);
	}

	@Override
	public float getBiomeMoisture(World world, BlockPos pos) {
		IClimateState info = ForestryAPI.climateManager.getClimateState(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		double moisture = 3.2 * (humidity - 0.5) - 0.4 * (1.0 + temperature + 0.5 * temperature * temperature) + 1.1 - 1.6 * (temperature - 0.9) * (temperature - 0.9) - 0.002 * (pos.getY() - 64);
		return (float) ((moisture == 0.0) ? moisture : ((moisture < 0.0) ? (-Math.sqrt(moisture * moisture)) : Math.sqrt(moisture * moisture)));
	}

	@Override
	public float getBiomePH(World world, BlockPos pos) {
		IClimateState info = ForestryAPI.climateManager.getClimateState(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		return (float) (-3.0 * (humidity - 0.5) + 0.5 * (temperature - 0.699999988079071) * (temperature - 0.699999988079071) + 0.02f * (pos.getY() - 64) - 0.15000000596046448);
	}

	@Override
	public IBlockState getSoil(EnumSoilType soil, boolean weedKill, EnumMoisture moisture, EnumAcidity acidity) {
		IBlockState blockState = getSoilBlock(soil, weedKill).getDefaultState();
		return blockState.withProperty(BlockSoil.MOISTURE, moisture).withProperty(BlockSoil.ACIDITY, acidity);
	}

	@Override
	public void plantSoil(World world, BlockPos pos, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity) {
		IBlockState blockState = getSoil(soil, false, moisture, acidity);
		world.setBlockState(pos, blockState, 2);
	}

	public Map<EnumFertiliserType, Map<ItemStack, Integer>> getFertilisers() {
		return fertilisers;
	}

	@Override
	public Collection<ItemStack> getFertilisers(EnumFertiliserType type) {
	Set<ItemStack> items = fertilisers.get(type).keySet();
		return Collections.unmodifiableSet(items);
	}

	@Override
	public boolean isFertiliser(EnumFertiliserType type, ItemStack stack) {
		Collection<ItemStack> fertilisers = getFertilisers(type);
		for (ItemStack fertiliser : fertilisers) {
			if (!fertiliser.isEmpty() && fertiliser.isItemEqual(stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void registerFertiliser(EnumFertiliserType type, ItemStack stack, int strength) {
		fertilisers.get(type).put(stack, strength);
	}

	@Override
	public int getFertiliserStrength(ItemStack stack) {
		for(EnumFertiliserType type : EnumFertiliserType.values()) {
			for (Map.Entry<ItemStack, Integer> entry : fertilisers.get(type).entrySet()) {
				if (entry.getKey().isItemEqual(stack)) {
					return entry.getValue();
				}
			}
		}
		return 1;
	}

	@Override
	public boolean onFertiliseSoil(ItemStack heldItem, IBlockSoil soil, World world, BlockPos pos, EntityPlayer player) {
		int fertiliserStrength = getFertiliserStrength(heldItem);
		if (isFertiliser(EnumFertiliserType.NUTRIENT, heldItem) && soil.getType(world, pos) != EnumSoilType.FLOWERBED) {
			EnumSoilType type = soil.getType(world, pos);
			int next = Math.min(type.ordinal() + fertiliserStrength, 2);
			if (soil.fertilise(world, pos, EnumSoilType.values()[next])) {
				if(!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
				}
				return true;
			}
		}

		if (isFertiliser(EnumFertiliserType.ACID, heldItem) && soil.getPH(world, pos) != EnumAcidity.ACID) {
			EnumAcidity pH = soil.getPH(world, pos);
			int next = Math.max(pH.ordinal() - fertiliserStrength, 0);
			if (soil.setPH(world, pos, EnumAcidity.values()[next])) {
				if(!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
				}
				return true;
			}
		}

		if (isFertiliser(EnumFertiliserType.ALKALINE, heldItem) && soil.getPH(world, pos) != EnumAcidity.ALKALINE) {
			EnumAcidity pH = soil.getPH(world, pos);
			int next = Math.min(pH.ordinal() + fertiliserStrength, 2);
			if (soil.setPH(world, pos, EnumAcidity.values()[next])) {
				if(!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canTolerate(@Nullable IFlower flower, World world, BlockPos pos) {
		if (flower == null) {
			return false;
		}
		IBlockState soil = world.getBlockState(pos.down());
		Biome biome = world.getBiome(pos);
		EnumAcidity acidity = soil.getValue(BlockSoil.ACIDITY);
		EnumMoisture moisture = soil.getValue(BlockSoil.MOISTURE);
		EnumTemperature temperature = EnumTemperature.getFromValue(biome.getTemperature());
		return canTolerate(flower, acidity, moisture, temperature);
	}

	@Override
	public EnumSoilType getSoilType(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock() instanceof IBlockSoil) {
			IBlockSoil soil = ((IBlockSoil) blockState.getBlock());
			return soil.getType(world, pos);
		}
		return EnumSoilType.SOIL;
	}

	@Override
	public Block getSoilBlock(EnumSoilType type) {
		return getSoilBlock(type, false);
	}

	@Override
	public Block getSoilBlock(EnumSoilType type, boolean weedKill) {
		switch (type) {
			case FLOWERBED:
				return weedKill ? ModuleGardening.flowerbedNoWeed : ModuleGardening.flowerbed;

			case LOAM:
				return weedKill ? ModuleGardening.loamNoWeed : ModuleGardening.loam;
			default:
				return weedKill ? ModuleGardening.soilNoWeed : ModuleGardening.soil;
		}
	}

	@Override
	public boolean canTolerate(IFlower flower, EnumAcidity acidity, EnumMoisture moisture, EnumTemperature temperature) {
		IFlowerGenome genome = flower.getGenome();
		return genome.canTolerate(acidity) && genome.canTolerate(moisture) && genome.canTolerate(temperature);
	}

	@Override
	public boolean isWeedkiller(ItemStack heldItem) {
		return OreDictionaryUtil.hasOreName(heldItem, "weedkiller");
	}

	@Override
	public boolean addWeedKiller(World world, BlockPos pos) {
		IBlockState oldState = world.getBlockState(pos);
		if (!(oldState.getBlock() instanceof IBlockSoil)) {
			return false;
		}

		EnumSoilType type = getSoilType(world, pos);
		IBlockState newState = getSoil(type, true,  oldState.getValue(BlockSoil.MOISTURE), oldState.getValue(BlockSoil.ACIDITY));
		boolean done = world.setBlockState(pos, newState, 2);
		if (done && BlockPlant.isWeed(world, pos.up())) {
			world.setBlockToAir(pos.up());
		}
		return done;
	}
}
