package binnie.core.network.packet;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;

interface IPacketLocation {
	TileEntity getTarget(World world);

	int getX();

	int getY();

	int getZ();
}
