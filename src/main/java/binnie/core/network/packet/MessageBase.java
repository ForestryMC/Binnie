package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MessageBase {
	private int id;

	public MessageBase(final int id) {
		this.id = id;
	}

	public MessageBase(final MessageBinnie message) {
		try {
			this.readData(message.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MessageBinnie getMessage() {
		return new MessageBinnie(id, this);
	}

	protected NBTTagCompound readNBTTagCompound(final ByteBuf data) throws IOException {
		final short length = data.readShort();
		if (length < 0) {
			return null;
		}

		final byte[] compressed = new byte[length];
		data.readBytes(compressed);
		return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
	}

	protected void writeNBTTagCompound(final NBTTagCompound nbttagcompound, final ByteBuf data) throws IOException {
		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			final byte[] compressed = CompressedStreamTools.compress(nbttagcompound);
			data.writeShort((short) compressed.length);
			data.writeBytes(compressed);
		}
	}

	public void writeData(final ByteBuf data) throws IOException {
		// ignored
	}

	public void readData(final ByteBuf data) throws IOException {
		// ignored
	}
}
