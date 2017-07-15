package binnie.botany.gardening;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import forestry.api.climate.IClimateInfo;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ForestryAPI;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import binnie.botany.api.IFlower;
import binnie.botany.api.IGardeningManager;
import binnie.botany.items.BotanyItems;

public class Gardening implements IGardeningManager {
	private final Map<ItemStack, Integer> fertiliserAcid = new LinkedHashMap<>();
	private final Map<ItemStack, Integer> fertiliserAlkaline = new LinkedHashMap<>();
	private final Map<ItemStack, Integer> fertiliserNutrient = new LinkedHashMap<>();
	
	public Gardening() {
		// ignored
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
		float bias = getBiomeMoisture(world, pos);
		for (int dx = -1; dx < 2; ++dx) {
			for (int dz = -1; dz < 2; ++dz) {
				if (dx != 0 || dz != 0) {
					if (world.getBlockState(pos.add(dx, 0, dz)) == Blocks.SAND) {
						bias -= 1.5;
					} else if (world.getBlockState(pos.add(dx, 0, dz)) == Blocks.WATER) {
						bias += 1.5;
					}
				}
			}
		}
		
		if (world.isRaining() && world.canBlockSeeSky(pos.up()) && world.getPrecipitationHeight(pos).getY() <= pos.getY() + 1) {
			bias += 1.5;
		}
		
		for (int dx = -1; dx < 2; ++dx) {
			for (int dz = -1; dz < 2; ++dz) {
				if (dx != 0 || dz != 0) {
					if (world.getBlockState(pos.add(dx, 0, dz)).getBlock() == Blocks.GRAVEL && bias > 0.0f) {
						bias *= 0.4f;
					}
				}
			}
		}
		return (bias <= -1.0f) ? EnumMoisture.DRY : ((bias >= 1.0f) ? EnumMoisture.DAMP : EnumMoisture.NORMAL);
	}
	
	@Override
	public EnumAcidity getNaturalPH(World world, BlockPos pos) {
		float bias = getBiomePH(world, pos);
		if (bias <= -1.0f) {
			return EnumAcidity.ACID;
		}
		return (bias >= 1.0f) ? EnumAcidity.ALKALINE : EnumAcidity.NEUTRAL;
	}
	
	@Override
	public float getBiomeMoisture(World world, BlockPos pos) {
		IClimateInfo info = ForestryAPI.climateManager.getInfo(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		double m = 3.2 * (humidity - 0.5) - 0.4 * (1.0 + temperature + 0.5 * temperature * temperature) + 1.1 - 1.6 * (temperature - 0.9) * (temperature - 0.9) - 0.002 * (pos.getY() - 64);
		return (float) ((m == 0.0) ? m : ((m < 0.0) ? (-Math.sqrt(m * m)) : Math.sqrt(m * m)));
	}
	
	@Override
	public float getBiomePH(World world, BlockPos pos) {
		IClimateInfo info = ForestryAPI.climateManager.getInfo(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		return (float) (-3.0 * (humidity - 0.5) + 0.5 * (temperature - 0.699999988079071) * (temperature - 0.699999988079071) + 0.02f * (pos.getY() - 64) - 0.15000000596046448);
	}
	
	@Override
	public void plantSoil(World world, BlockPos pos, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity) {
		int meta = moisture.ordinal() + acidity.ordinal() * 3;
		world.setBlockState(pos, getSoilBlock(soil).getStateFromMeta(meta), 2);
	}
	
	@Override
	public Collection<ItemStack> getAcidFertilisers() {
		return Collections.unmodifiableSet(fertiliserAcid.keySet());
	}
	
	@Override
	public Collection<ItemStack> getAlkalineFertilisers() {
		return Collections.unmodifiableSet(fertiliserAlkaline.keySet());
	}
	
	@Override
	public Collection<ItemStack> getNutrientFertilisers() {
		return Collections.unmodifiableSet(fertiliserNutrient.keySet());
	}
	
	@Override
	public void registerAcidFertiliser(ItemStack stack, int strength) {
		ModuleGardening.queuedAcidFertilisers.put(stack, strength);
		fertiliserAcid.put(stack, strength);
	}
	
	@Override
	public void registerAlkalineFertiliser(ItemStack stack, int strength) {
		ModuleGardening.queuedAlkalineFertilisers.put(stack, strength);
		fertiliserAlkaline.put(stack, strength);
	}
	
	@Override
	public void registerNutrientFertiliser(ItemStack stack, int strength) {
		ModuleGardening.queuedNutrientFertilisers.put(stack, strength);
		fertiliserNutrient.put(stack, strength);
	}
	
	@Override
	public boolean isAcidFertiliser(ItemStack itemstack) {
		for (ItemStack stack : getAcidFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isAlkalineFertiliser(ItemStack itemstack) {
		for (ItemStack stack : getAlkalineFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isNutrientFertiliser(ItemStack itemstack) {
		for (ItemStack stack : getNutrientFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getFertiliserStrength(ItemStack stack) {
		for (Map.Entry<ItemStack, Integer> entry : fertiliserAcid.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		for (Map.Entry<ItemStack, Integer> entry : fertiliserAlkaline.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		for (Map.Entry<ItemStack, Integer> entry : fertiliserNutrient.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		return 1;
	}
	
	@Override
	public boolean canTolerate(@Nullable IFlower flower, World world, BlockPos pos) {
		if (flower == null) {
			return false;
		}
		IBlockState soil = world.getBlockState(pos.down());
		Block soilBlock = soil.getBlock();
		int soilMeta = soilBlock.getMetaFromState(soil);
		Biome biome = world.getBiome(pos);
		return canTolerate(
				flower,
				EnumAcidity.values()[soilMeta / 3 % 3],
				EnumMoisture.values()[soilMeta % 3],
				EnumTemperature.getFromValue(biome.getTemperature())
		);
	}
	
	@Override
	public EnumSoilType getSoilType(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof IBlockSoil) {
			return ((IBlockSoil) world.getBlockState(pos).getBlock()).getType(world, pos);
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
				return weedKill ? Botany.flowerbedNoWeed : Botany.flowerbed;
			
			case LOAM:
				return weedKill ? Botany.loamNoWeed : Botany.loam;
		}
		return weedKill ? Botany.soilNoWeed : Botany.soil;
	}
	
	@Override
	public boolean canTolerate(IFlower flower, EnumAcidity acidity, EnumMoisture moisture, EnumTemperature temperature) {
		return flower.getGenome().canTolerate(acidity)
				&& flower.getGenome().canTolerate(moisture)
				&& flower.getGenome().canTolerate(temperature);
	}
	
	@Override
	public boolean isWeedkiller(ItemStack heldItem) {
		return heldItem.isItemEqual(BotanyItems.WEEDKILLER.get(1));
	}
	
	@Override
	public boolean addWeedKiller(World world, BlockPos pos) {
		if (!(world.getBlockState(pos).getBlock() instanceof IBlockSoil)) {
			return false;
		}
		
		EnumSoilType type = getSoilType(world, pos);
		Block block = getSoilBlock(type, true);
		IBlockState oldState = world.getBlockState(pos);
		IBlockState newState = block.getStateFromMeta(oldState.getBlock().getMetaFromState(oldState));
		boolean done = world.setBlockState(pos, newState, 2);
		if (done && BlockPlant.isWeed(world, pos.up())) {
			world.setBlockToAir(pos.up());
		}
		return done;
	}
}
