package binnie.core.network.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

interface IPacketLocation {
	@Nullable
	TileEntity getTarget(final World world);

	int getX();

	int getY();

	int getZ();
}
