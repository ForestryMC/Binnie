package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import binnie.core.util.Log;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import io.netty.buffer.ByteBuf;

public class MessageBase {
	private int id;

	public MessageBase(final int id) {
		this.id = id;
	}

	public MessageBase(final MessageBinnie message) {
		try {
			this.readData(message.getData());
		} catch (IOException e) {
			Log.error("Failed to read message data.", e);
		}
	}

	public MessageBinnie GetMessage() {
		return new MessageBinnie(this.id, this);
	}

	@Nullable
	protected NBTTagCompound readNBTTagCompound(final ByteBuf data) throws IOException {
		final short length = data.readShort();
		if (length < 0) {
			return null;
		}
		final byte[] compressed = new byte[length];
		data.readBytes(compressed);
		return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
	}

	protected void writeNBTTagCompound(@Nullable final NBTTagCompound nbttagcompound, final ByteBuf data) throws IOException {
		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			CompressedStreamTools.writeCompressed(nbttagcompound, o);
			o.flush();
			byte[] compressed = o.toByteArray();
			data.writeShort((short) compressed.length);
			data.writeBytes(compressed);
		}
	}

	public void writeData(final ByteBuf data) throws IOException {
	}

	public void readData(final ByteBuf data) throws IOException {
	}
}
