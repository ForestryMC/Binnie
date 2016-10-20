package binnie.core.network.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

interface IPacketLocation {
    TileEntity getTarget(final World p0);

    int getX();

    int getY();

    int getZ();
}
