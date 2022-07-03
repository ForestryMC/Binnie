package binnie.botany.api.gardening;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import net.minecraft.world.World;

public interface IBlockSoil {
    EnumAcidity getPH(World world, int x, int y, int z);

    EnumMoisture getMoisture(World world, int x, int y, int z);

    EnumSoilType getType(World world, int x, int y, int z);

    boolean fertilise(World world, int x, int y, int z, EnumSoilType maxLevel);

    boolean degrade(World world, int x, int y, int z, EnumSoilType minLevel);

    boolean setPH(World world, int x, int y, int z, EnumAcidity pH);

    boolean setMoisture(World world, int x, int y, int z, EnumMoisture p4);

    boolean resistsWeeds(World world, int x, int y, int z);
}
