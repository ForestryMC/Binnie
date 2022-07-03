package binnie.core.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class MessageBinnie implements IMessage {
    public int id;
    private MessageBase message;
    protected ByteBuf data;

    public MessageBinnie() {
        // ignored
    }

    public MessageBinnie(int id, MessageBase message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(id);
        try {
            message.writeData(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readByte();
        data = buf;
    }
}
