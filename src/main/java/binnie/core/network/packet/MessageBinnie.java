// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network.packet;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public final class MessageBinnie implements IMessage
{
	public int id;
	private MessageBase message;
	ByteBuf data;

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
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.id = buf.readByte();
		this.data = buf;
	}
}
