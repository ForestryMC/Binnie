package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;


public final class MessageBinnie implements IMessage {
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
