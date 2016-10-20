package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public class MessageNBT extends MessageBase {
    NBTTagCompound nbt;

    public NBTTagCompound getTagCompound() {
        return this.nbt;
    }

    void setTagCompound(final NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    public MessageNBT(final int id) {
        super(id);
    }

    public MessageNBT(final int id, final NBTTagCompound nbt) {
        this(id);
        this.setTagCompound(nbt);
    }

    public MessageNBT(final MessageBinnie message) {
        super(message);
    }

    @Override
    public void writeData(final ByteBuf data) throws IOException {
        this.writeNBTTagCompound(this.nbt, data);
    }

    @Override
    public void readData(final ByteBuf data) throws IOException {
        this.nbt = this.readNBTTagCompound(data);
    }
}
