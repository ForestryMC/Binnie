package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.minecraft.tileentity.TileEntity;

import binnie.core.network.INetworkedEntity;

import io.netty.buffer.ByteBuf;

public class MessageUpdate extends MessageCoordinates {
	@Nullable
	private PacketPayload payload;

	public MessageUpdate(MessageBinnie message) {
		super(message);
	}

	public MessageUpdate(int id, INetworkedEntity tile) {
		super(id, ((TileEntity) tile).getPos());
		tile.writeToPacket(this.payload = new PacketPayload());
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
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
		for (int intData : this.payload.getIntPayload()) {
			data.writeInt(intData);
		}
		for (float floatData : this.payload.getFloatPayload()) {
			data.writeFloat(floatData);
		}
		for (String stringData : this.payload.getStringPayload()) {
			byte[] bytes = stringData.getBytes(StandardCharsets.UTF_8);
			data.writeShort(bytes.length);
			data.writeBytes(bytes);
		}
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		super.readData(data);
		this.payload = new PacketPayload();
		int intLength = data.readInt();
		int floatLength = data.readInt();
		int stringLength = data.readInt();
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
			int length = data.readShort();
			byte[] string = data.readBytes(length).array();
			this.payload.addString(new String(string, StandardCharsets.UTF_8));
		}
	}

	@Nullable
	public PacketPayload getPayload() {
		return payload;
	}
}
