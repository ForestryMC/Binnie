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

    public MessageFlowerUpdate(final int posX, final int posY, final int posZ, final TileEntityFlower.RenderInfo render) {
        super(PacketID.FlowerUpdate.ordinal(), posX, posY, posZ);
        this.render = render;
    }
    
    @Override
    public MessageBinnie GetMessage() {
    	return super.GetMessage();
    }

    public MessageFlowerUpdate(final MessageBinnie message) {
        super(message);
    }

    @Override
    public void writeData(final ByteBuf data) throws IOException {
        super.writeData(data);
        data.writeByte(this.render.primary.getID());
        data.writeByte(this.render.secondary.getID());
        data.writeByte(this.render.stem.getID());
        data.writeByte(this.render.type.ordinal());
        data.writeByte(this.render.age);
        data.writeByte(this.render.section);
        data.writeBoolean(this.render.wilted);
        data.writeBoolean(this.render.flowered);
    }

    @Override
    public void readData(final ByteBuf data) throws IOException {
        super.readData(data);
        this.render = new TileEntityFlower.RenderInfo();
        this.render.primary = EnumFlowerColor.values()[data.readByte()];
        this.render.secondary = EnumFlowerColor.values()[data.readByte()];
        this.render.stem = EnumFlowerColor.values()[data.readByte()];
        this.render.type = EnumFlowerType.values()[data.readByte()];
        this.render.age = data.readByte();
        this.render.section = data.readByte();
        this.render.wilted = data.readBoolean();
        this.render.flowered = data.readBoolean();
    }
}
