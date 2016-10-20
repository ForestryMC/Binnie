// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network.packet;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import binnie.core.network.BinnieCorePacketID;

public class MessageMetadata extends MessageCoordinates
{
	public int meta;

	public MessageMetadata(final int posX, final int posY, final int posZ, final int meta) {
		super(BinnieCorePacketID.TileMetadata.ordinal(), posX, posY, posZ);
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
}
