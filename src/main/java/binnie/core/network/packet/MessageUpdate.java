package binnie.core.network.packet;

import binnie.core.network.INetworkedEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.IOException;

public class MessageUpdate extends MessageCoordinates {
	@Nullable
	public PacketPayload payload;

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
		data.writeInt(this.payload.intPayload.size());
		data.writeInt(this.payload.floatPayload.size());
		data.writeInt(this.payload.stringPayload.size());
		for (final int intData : this.payload.intPayload) {
			data.writeInt(intData);
		}
		for (final float floatData : this.payload.floatPayload) {
			data.writeFloat(floatData);
		}
		for (final String stringData : this.payload.stringPayload) {
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
		this.payload.intPayload.clear();
		this.payload.floatPayload.clear();
		this.payload.stringPayload.clear();
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
	public TileEntity getTarget(final World world) {
		return world.getTileEntity(getCoordinates());
	}
}
