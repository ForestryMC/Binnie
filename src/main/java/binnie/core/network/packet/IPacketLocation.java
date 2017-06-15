package binnie.core.network.packet;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

interface IPacketLocation {
	@Nullable
	TileEntity getTarget(final World world);

	int getX();

	int getY();

	int getZ();
}
