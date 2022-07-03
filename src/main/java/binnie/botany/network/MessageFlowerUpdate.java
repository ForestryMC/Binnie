package binnie.botany.network;

import binnie.botany.flower.TileEntityFlower;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageCoordinates;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class MessageFlowerUpdate extends MessageCoordinates {
    public TileEntityFlower.RenderInfo render;

    public MessageFlowerUpdate(int posX, int posY, int posZ, TileEntityFlower.RenderInfo render) {
        super(PacketID.FlowerUpdate.ordinal(), posX, posY, posZ);
        this.render = render;
    }

    public MessageFlowerUpdate(MessageBinnie message) {
        super(message);
    }

    @Override
    public void writeData(ByteBuf data) throws IOException {
        super.writeData(data);
        data.writeByte(render.primary.getID());
        data.writeByte(render.secondary.getID());
        data.writeByte(render.stem.getID());
        data.writeByte(render.type.ordinal());
        data.writeByte(render.age);
        data.writeByte(render.section);
        data.writeBoolean(render.wilted);
        data.writeBoolean(render.flowered);
    }

    @Override
    public void readData(ByteBuf data) throws IOException {
        super.readData(data);
        render = new TileEntityFlower.RenderInfo();
        render.primary = EnumFlowerColor.values()[data.readByte()];
        render.secondary = EnumFlowerColor.values()[data.readByte()];
        render.stem = EnumFlowerColor.values()[data.readByte()];
        render.type = EnumFlowerType.values()[data.readByte()];
        render.age = data.readByte();
        render.section = data.readByte();
        render.wilted = data.readBoolean();
        render.flowered = data.readBoolean();
    }
}
