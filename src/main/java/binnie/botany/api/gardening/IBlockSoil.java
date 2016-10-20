// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api.gardening;

import binnie.botany.api.EnumSoilType;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumAcidity;
import net.minecraft.world.World;

public interface IBlockSoil
{
	EnumAcidity getPH(final World p0, final int p1, final int p2, final int p3);

	EnumMoisture getMoisture(final World p0, final int p1, final int p2, final int p3);

	EnumSoilType getType(final World p0, final int p1, final int p2, final int p3);

	boolean fertilise(final World p0, final int p1, final int p2, final int p3, final EnumSoilType p4);

	boolean degrade(final World p0, final int p1, final int p2, final int p3, final EnumSoilType p4);

	boolean setPH(final World p0, final int p1, final int p2, final int p3, final EnumAcidity p4);

	boolean setMoisture(final World p0, final int p1, final int p2, final int p3, final EnumMoisture p4);

	boolean resistsWeeds(final World p0, final int p1, final int p2, final int p3);
}
