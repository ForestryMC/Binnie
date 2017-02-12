package binnie.core.network.packet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class PacketMetadata extends SPacketUpdateTileEntity {

	public int metadata;

	public PacketMetadata(BlockPos blockPos, int metadata, NBTTagCompound compound) {
		super(blockPos, 0, compound);
		this.metadata = metadata;
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		super.writePacketData(buf);
		buf.writeInt(metadata);
	}

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		super.readPacketData(buf);
		metadata = buf.readInt();
	}

	public int getMetadata() {
		return metadata;
	}

}
