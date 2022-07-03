package binnie.core.network.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

interface IPacketLocation {
    TileEntity getTarget(World world);

    int getX();

    int getY();

    int getZ();
}
