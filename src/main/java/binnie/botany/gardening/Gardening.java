package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import binnie.botany.api.IFlower;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.items.BotanyItems;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.climate.IClimateInfo;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ForestryAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Gardening {
	public static final Map<ItemStack, Integer> fertiliserAcid = new LinkedHashMap<>();
	public static final Map<ItemStack, Integer> fertiliserAlkaline = new LinkedHashMap<>();
	public static final Map<ItemStack, Integer> fertiliserNutrient = new LinkedHashMap<>();

	public static boolean isSoil(final Block block) {
		return block instanceof IBlockSoil;
	}

	public static boolean isSoil(final Item item) {
		return item instanceof ItemBlock && isSoil(((ItemBlock) item).getBlock());
	}

	public static boolean isSoil(final ItemStack item) {
		return isSoil(item.getItem());
	}

	public static EnumMoisture getNaturalMoisture(World world, BlockPos pos) {
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

	public static EnumAcidity getNaturalPH(World world, BlockPos pos) {
		final float bias = getBiomePH(world, pos);
		return (bias <= -1.0f) ? EnumAcidity.ACID : ((bias >= 1.0f) ? EnumAcidity.ALKALINE : EnumAcidity.NEUTRAL);
	}

	public static float getBiomeMoisture(World world, BlockPos pos) {
		IClimateInfo info = ForestryAPI.climateManager.getInfo(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		double m = 3.2 * (humidity - 0.5) - 0.4 * (1.0 + temperature + 0.5 * temperature * temperature) + 1.1 - 1.6 * (temperature - 0.9) * (temperature - 0.9) - 0.002 * (pos.getY() - 64);
		return (float) ((m == 0.0) ? m : ((m < 0.0) ? (-Math.sqrt(m * m)) : Math.sqrt(m * m)));
	}

	public static float getBiomePH(World world, BlockPos pos) {
		IClimateInfo info = ForestryAPI.climateManager.getInfo(world, pos);
		double humidity = info.getHumidity();
		double temperature = info.getTemperature();
		return (float) (-3.0 * (humidity - 0.5) + 0.5 * (temperature - 0.699999988079071) * (temperature - 0.699999988079071) + 0.02f * (pos.getY() - 64) - 0.15000000596046448);
	}

	public static void plantSoil(final World world, final BlockPos pos, final EnumSoilType soil, final EnumMoisture moisture, final EnumAcidity acidity) {
		final int meta = moisture.ordinal() + acidity.ordinal() * 3;
		world.setBlockState(pos, getSoilBlock(soil).getStateFromMeta(meta), 2);
	}

	public static boolean plant(final World world, final BlockPos pos, final IFlower flower, final GameProfile owner) {
		final boolean set = world.setBlockState(pos, Botany.flower.getDefaultState());
		if (!set) {
			return false;
		}
		final TileEntity tileFlower = world.getTileEntity(pos);
		final TileEntity below = world.getTileEntity(pos.down());
		if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
			if (below instanceof TileEntityFlower) {
				((TileEntityFlower) tileFlower).setSection(((TileEntityFlower) below).getSection());
			} else {
				((TileEntityFlower) tileFlower).create(flower, owner);
			}
		}
		tryGrowSection(world, pos);
		return true;
	}

	public static void tryGrowSection(final World world, final BlockPos pos) {
		if (!BinnieCore.getBinnieProxy().isSimulating(world)) {
			return;
		}
		final TileEntity flower = world.getTileEntity(pos);
		if (flower != null && flower instanceof TileEntityFlower) {
			final IFlower iflower = ((TileEntityFlower) flower).getFlower();
			final int section = ((TileEntityFlower) flower).getSection();
			if (iflower != null && section < iflower.getGenome().getPrimary().getType().getSections() - 1 && iflower.getAge() > 0) {
				world.setBlockState(pos.up(), Botany.flower.getDefaultState());
				final TileEntity tileFlower = world.getTileEntity(pos.up());
				if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
					((TileEntityFlower) tileFlower).setSection(section + 1);
				}
			}
		}
	}

	public static void onGrowFromSeed(final World world, final BlockPos pos) {
		tryGrowSection(world, pos);
	}

	public static Collection<ItemStack> getAcidFertilisers() {
		return Gardening.fertiliserAcid.keySet();
	}

	public static Collection<ItemStack> getAlkalineFertilisers() {
		return Gardening.fertiliserAlkaline.keySet();
	}

	public static Collection<ItemStack> getNutrientFertilisers() {
		return Gardening.fertiliserNutrient.keySet();
	}

	public static boolean isAcidFertiliser(final ItemStack itemstack) {
		for (final ItemStack stack : getAcidFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAlkalineFertiliser(final ItemStack itemstack) {
		for (final ItemStack stack : getAlkalineFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNutrientFertiliser(final ItemStack itemstack) {
		for (final ItemStack stack : getNutrientFertilisers()) {
			if (!stack.isEmpty() && stack.isItemEqual(itemstack)) {
				return true;
			}
		}
		return false;
	}

	public static int getFertiliserStrength(final ItemStack stack) {
		for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAcid.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAlkaline.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserNutrient.entrySet()) {
			if (entry.getKey().isItemEqual(stack)) {
				return entry.getValue();
			}
		}
		return 1;
	}

	public static boolean canTolerate(@Nullable final IFlower flower, final World world, final BlockPos pos) {
		if (flower == null) {
			return false;
		}
		IBlockState soil = world.getBlockState(pos.down());
		final Block soilBlock = soil.getBlock();
		final int soilMeta = soilBlock.getMetaFromState(soil);
		final Biome biome = world.getBiome(pos);
		return canTolerate(flower, EnumAcidity.values()[soilMeta / 3 % 3], EnumMoisture.values()[soilMeta % 3], EnumTemperature.getFromValue(biome.getTemperature()));
	}

	public static EnumSoilType getSoilType(final World world, final BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof IBlockSoil) {
			return ((IBlockSoil) world.getBlockState(pos).getBlock()).getType(world, pos);
		}
		return EnumSoilType.SOIL;
	}

	public static Block getSoilBlock(final EnumSoilType type) {
		return getSoilBlock(type, false);
	}

	public static Block getSoilBlock(final EnumSoilType type, final boolean weedkill) {
		switch (type) {
			case FLOWERBED: {
				return weedkill ? Botany.flowerbedNoWeed : Botany.flowerbed;
			}
			case LOAM: {
				return weedkill ? Botany.loamNoWeed : Botany.loam;
			}
			default: {
				return weedkill ? Botany.soilNoWeed : Botany.soil;
			}
		}
	}

	public static boolean canTolerate(IFlower flower, EnumAcidity ePH, EnumMoisture eMoisture, EnumTemperature eTemp) {
		return flower.getGenome().canTolerate(ePH) && flower.getGenome().canTolerate(eMoisture) && flower.getGenome().canTolerate(eTemp);
	}

	public static boolean isWeedkiller(ItemStack heldItem) {
		return heldItem.isItemEqual(BotanyItems.Weedkiller.get(1));
	}

	public static boolean addWeedKiller(World world, BlockPos pos) {
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
