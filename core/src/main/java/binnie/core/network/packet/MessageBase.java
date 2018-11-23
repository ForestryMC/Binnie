package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import binnie.core.util.Log;

import io.netty.buffer.ByteBuf;

public class MessageBase {
	private int id;

	public MessageBase(int id) {
		this.id = id;
	}

	public MessageBase(MessageBinnie message) {
		ByteBuf data = message.getData();
		try {
			this.readData(data);
		} catch (IOException e) {
			Log.error("Failed to read message data.", e);
		}
		data.release();
	}

	public MessageBinnie GetMessage() {
		return new MessageBinnie(this.id, this);
	}

	@Nullable
	protected NBTTagCompound readNBTTagCompound(ByteBuf data) throws IOException {
		short length = data.readShort();
		if (length < 0) {
			return null;
		}
		byte[] compressed = new byte[length];
		data.readBytes(compressed);
		return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
	}

	protected void writeNBTTagCompound(@Nullable NBTTagCompound nbttagcompound, ByteBuf data) throws IOException {
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

	public void writeData(ByteBuf data) throws IOException {
	}

	public void readData(ByteBuf data) throws IOException {
	}
}
