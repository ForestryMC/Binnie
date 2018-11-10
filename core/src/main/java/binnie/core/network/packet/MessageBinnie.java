package binnie.core.network.packet;

import java.io.IOException;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import binnie.core.util.Log;

import io.netty.buffer.ByteBuf;

public final class MessageBinnie implements IMessage {
	private int id;
	private ByteBuf data;
	private MessageBase message;

	public MessageBinnie() {
	}

	public MessageBinnie(final int id, final MessageBase base) {
		this.id = id;
		this.message = base;
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeByte(this.id);
		try {
			this.message.writeData(buf);
		} catch (IOException e) {
			Log.error("Failed to write message data.", e);
		}
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.id = buf.readByte();
		this.data = buf.retain();
	}

	public int getId() {
		return id;
	}

	public ByteBuf getData() {
		return data;
	}
}
