package binnie.botany.api.gardening;

import binnie.botany.api.genetics.IFlower;
import forestry.api.core.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

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

	IBlockState getSoil(EnumSoilType soil, boolean weedKill, EnumMoisture moisture, EnumAcidity acidity);

	void plantSoil(World world, BlockPos pos, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity);

	Collection<ItemStack> getFertilisers(EnumFertiliserType type);

	Map<EnumFertiliserType, Map<ItemStack, Integer>> getFertilisers();

	void registerFertiliser(EnumFertiliserType type, ItemStack stack, int strength);

	boolean isFertiliser(EnumFertiliserType type, ItemStack stack);

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

	boolean onFertiliseSoil(ItemStack heldItem, IBlockSoil soil, World world, BlockPos pos, EntityPlayer player);
}
