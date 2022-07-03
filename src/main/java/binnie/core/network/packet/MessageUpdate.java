package binnie.core.network.packet;

import binnie.core.network.INetworkedEntity;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MessageUpdate extends MessageCoordinates {
    public PacketPayload payload;

    public MessageUpdate(MessageBinnie message) {
        super(message);
    }

    public MessageUpdate(int id, INetworkedEntity tile) {
        super(id, ((TileEntity) tile).xCoord, ((TileEntity) tile).yCoord, ((TileEntity) tile).zCoord);
        payload = new PacketPayload();
        tile.writeToPacket(payload);
    }

    @Override
    public void writeData(ByteBuf data) throws IOException {
        super.writeData(data);
        if (payload == null) {
            data.writeInt(0);
            data.writeInt(0);
            data.writeInt(0);
            return;
        }

        data.writeInt(payload.intPayload.size());
        data.writeInt(payload.floatPayload.size());
        data.writeInt(payload.stringPayload.size());

        for (int intData : payload.intPayload) {
            data.writeInt(intData);
        }
        for (float floatData : payload.floatPayload) {
            data.writeFloat(floatData);
        }
        for (String stringData : payload.stringPayload) {
            byte[] bytes = stringData.getBytes("UTF-8");
            data.writeShort(bytes.length);
            data.writeBytes(bytes);
        }
    }

    @Override
    public void readData(ByteBuf data) throws IOException {
        super.readData(data);
        payload = new PacketPayload();
        payload.intPayload.clear();
        payload.floatPayload.clear();
        payload.stringPayload.clear();

        int intLength = data.readInt();
        int floatLength = data.readInt();
        int stringLength = data.readInt();

        for (int i = 0; i < intLength; ++i) {
            payload.addInteger(data.readInt());
        }
        for (int i = 0; i < floatLength; ++i) {
            payload.addFloat(data.readFloat());
        }
        for (int i = 0; i < stringLength; ++i) {
            int length = data.readShort();
            byte[] string = data.readBytes(length).array();
            payload.addString(new String(string, "UTF-8"));
        }
    }

    // TODO unused method?
    public TileEntity getTarget(World world) {
        return world.getTileEntity(posX, posY, posZ);
    }
}
