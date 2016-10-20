package binnie.botany.api.gardening;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockSoil {
    EnumAcidity getPH(final World p0, final BlockPos pos);

    EnumMoisture getMoisture(final World p0, final BlockPos pos);

    EnumSoilType getType(final World p0, final BlockPos pos);

    boolean fertilise(final World p0, final BlockPos pos, final EnumSoilType p4);

    boolean degrade(final World p0, final BlockPos pos, final EnumSoilType p4);

    boolean setPH(final World p0, final BlockPos pos, final EnumAcidity p4);

    boolean setMoisture(final World p0, final BlockPos pos, final EnumMoisture p4);

    boolean resistsWeeds(final World p0, final BlockPos pos);
}
