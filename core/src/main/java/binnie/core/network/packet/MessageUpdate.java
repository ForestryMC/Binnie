package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.tileentity.TileEntity;

import binnie.core.network.INetworkedEntity;
import io.netty.buffer.ByteBuf;

public class MessageUpdate extends MessageCoordinates {
	@Nullable
	private PacketPayload payload;

	public MessageUpdate(final MessageBinnie message) {
		super(message);
	}

	public MessageUpdate(final int id, final INetworkedEntity tile) {
		super(id, ((TileEntity) tile).getPos());
		tile.writeToPacket(this.payload = new PacketPayload());
	}

	@Override
	public void writeData(final ByteBuf data) throws IOException {
		super.writeData(data);
		if (this.payload == null) {
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			return;
		}
		data.writeInt(this.payload.getIntPayload().size());
		data.writeInt(this.payload.getFloatPayload().size());
		data.writeInt(this.payload.getStringPayload().size());
		for (final int intData : this.payload.getIntPayload()) {
			data.writeInt(intData);
		}
		for (final float floatData : this.payload.getFloatPayload()) {
			data.writeFloat(floatData);
		}
		for (final String stringData : this.payload.getStringPayload()) {
			final byte[] bytes = stringData.getBytes("UTF-8");
			data.writeShort(bytes.length);
			data.writeBytes(bytes);
		}
	}

	@Override
	public void readData(final ByteBuf data) throws IOException {
		super.readData(data);
		this.payload = new PacketPayload();
		final int intLength = data.readInt();
		final int floatLength = data.readInt();
		final int stringLength = data.readInt();
		this.payload.getIntPayload().clear();
		this.payload.getFloatPayload().clear();
		this.payload.getStringPayload().clear();
		for (int i = 0; i < intLength; ++i) {
			this.payload.addInteger(data.readInt());
		}
		for (int i = 0; i < floatLength; ++i) {
			this.payload.addFloat(data.readFloat());
		}
		for (int i = 0; i < stringLength; ++i) {
			final int length = data.readShort();
			final byte[] string = data.readBytes(length).array();
			this.payload.addString(new String(string, "UTF-8"));
		}
	}

	@Nullable
	public PacketPayload getPayload() {
		return payload;
	}
}
