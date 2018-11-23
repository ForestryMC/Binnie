package binnie.core.network.packet;

import java.io.IOException;

import net.minecraft.util.math.BlockPos;

import binnie.core.network.BinnieCorePacketID;

import io.netty.buffer.ByteBuf;

public class MessageMetadata extends MessageCoordinates {
	private int meta;

	public MessageMetadata(BlockPos coordinates, int meta) {
		super(BinnieCorePacketID.TILE_METADATA.ordinal(), coordinates);
		this.meta = meta;
	}

	public MessageMetadata(MessageBinnie message) {
		super(message);
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		super.writeData(data);
		data.writeInt(this.meta);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		super.readData(data);
		this.meta = data.readInt();
	}

	public int getMeta() {
		return meta;
	}
}
