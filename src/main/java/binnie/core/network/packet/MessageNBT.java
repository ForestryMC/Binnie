package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;

public class MessageNBT extends MessageBase {
    protected NBTTagCompound nbt;

    public NBTTagCompound getTagCompound() {
        return nbt;
    }

    void setTagCompound(NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    public MessageNBT(int id) {
        super(id);
    }

    public MessageNBT(int id, NBTTagCompound nbt) {
        this(id);
        setTagCompound(nbt);
    }

    public MessageNBT(MessageBinnie message) {
        super(message);
    }

    @Override
    public void writeData(ByteBuf data) throws IOException {
        writeNBTTagCompound(nbt, data);
    }

    @Override
    public void readData(ByteBuf data) throws IOException {
        nbt = readNBTTagCompound(data);
    }
}
