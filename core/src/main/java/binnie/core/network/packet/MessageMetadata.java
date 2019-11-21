package binnie.core.network.packet;

import binnie.core.network.BinnieCorePacketID;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class MessageMetadata extends MessageCoordinates {
	private int meta;

	public MessageMetadata(BlockPos coordinates, int meta) {
		super(BinnieCorePacketID.TILE_METADATA.ordinal(), coordinates);
		this.meta = meta;
	}

	public MessageMetadata(final MessageBinnie message) {
		super(message);
	}

	@Override
	public void writeData(final ByteBuf data) throws IOException {
		super.writeData(data);
		data.writeInt(this.meta);
	}

	@Override
	public void readData(final ByteBuf data) throws IOException {
		super.readData(data);
		this.meta = data.readInt();
	}

	public int getMeta() {
		return meta;
	}
}
