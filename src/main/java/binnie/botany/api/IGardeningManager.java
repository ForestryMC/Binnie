package binnie.botany.api;

import forestry.api.core.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IGardeningManager {
	default boolean isSoil(Block block) {
		return block instanceof IBlockSoil;
	}

	boolean isSoil(Item item);

	boolean isSoil(ItemStack item);

	EnumMoisture getNaturalMoisture(World world, BlockPos pos);

	EnumAcidity getNaturalPH(World world, BlockPos pos);

	float getBiomeMoisture(World world, BlockPos pos);

	float getBiomePH(World world, BlockPos pos);

	void plantSoil(World world, BlockPos pos, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity);

	Collection<ItemStack> getAcidFertilisers();

	Collection<ItemStack> getAlkalineFertilisers();

	Collection<ItemStack> getNutrientFertilisers();

	void registerAcidFertiliser(ItemStack stack, int strength);

	void registerAlkalineFertiliser(ItemStack stack, int strength);

	void registerNutrientFertiliser(ItemStack stack, int strength);

	boolean isAcidFertiliser(ItemStack stack);

	boolean isAlkalineFertiliser(ItemStack stack);

	boolean isNutrientFertiliser(ItemStack stack);

	int getFertiliserStrength(ItemStack stack);

	boolean canTolerate(@Nullable IFlower flower, World world, BlockPos pos);

	EnumSoilType getSoilType(World world, BlockPos pos);

	default Block getSoilBlock(EnumSoilType type) {
		return getSoilBlock(type, false);
	}

	Block getSoilBlock(EnumSoilType type, boolean weedKill);

	boolean canTolerate(IFlower flower, EnumAcidity acidity, EnumMoisture moisture, EnumTemperature temperature);

	boolean isWeedkiller(ItemStack heldItem);

	boolean addWeedKiller(World world, BlockPos pos);
}
