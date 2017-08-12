package binnie.botany.api.gardening;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockSoil {
	EnumAcidity getPH(World world, BlockPos pos);

	EnumMoisture getMoisture(World world, BlockPos pos);

	EnumSoilType getType(World world, BlockPos pos);

	boolean fertilise(World world, BlockPos pos, EnumSoilType soilType);

	boolean degrade(World world, BlockPos pos, EnumSoilType soilType);

	boolean setPH(World world, BlockPos pos, EnumAcidity acidity);

	boolean setMoisture(World world, BlockPos pos, EnumMoisture moisture);

	boolean resistsWeeds(World world, BlockPos pos);
}
