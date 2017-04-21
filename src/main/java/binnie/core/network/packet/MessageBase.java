package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MessageBase {
	private int id;

	public MessageBase(int id) {
		this.id = id;
	}

	public MessageBase(MessageBinnie message) {
		try {
			this.readData(message.data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MessageBinnie getMessage() {
		return new MessageBinnie(id, this);
	}

	protected NBTTagCompound readNBTTagCompound(ByteBuf data) throws IOException {
		short length = data.readShort();
		if (length < 0) {
			return null;
		}

		byte[] compressed = new byte[length];
		data.readBytes(compressed);
		return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
	}

	protected void writeNBTTagCompound(NBTTagCompound nbttagcompound, ByteBuf data) throws IOException {
		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			byte[] compressed = CompressedStreamTools.compress(nbttagcompound);
			data.writeShort((short) compressed.length);
			data.writeBytes(compressed);
		}
	}

	public void writeData(ByteBuf data) throws IOException {
		// ignored
	}

	public void readData(ByteBuf data) throws IOException {
		// ignored
	}
}
