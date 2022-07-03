package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MessageTileNBT extends MessageNBT implements IPacketLocation {
    private int posX;
    private int posY;
    private int posZ;

    public MessageTileNBT(MessageBinnie message) {
        super(message);
    }

    public MessageTileNBT(int id, TileEntity tile, NBTTagCompound nbt) {
        super(id);
        posX = tile.xCoord;
        posY = tile.yCoord;
        posZ = tile.zCoord;
        this.nbt = nbt;
    }

    @Override
    public void writeData(ByteBuf data) throws IOException {
        data.writeInt(posX);
        data.writeInt(posY);
        data.writeInt(posZ);
        super.writeData(data);
    }

    @Override
    public void readData(ByteBuf data) throws IOException {
        posX = data.readInt();
        posY = data.readInt();
        posZ = data.readInt();
        super.readData(data);
    }

    @Override
    public TileEntity getTarget(World world) {
        return world.getTileEntity(posX, posY, posZ);
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public int getZ() {
        return posZ;
    }

    @Override
    public NBTTagCompound getTagCompound() {
        return nbt;
    }

    @Override
    void setTagCompound(NBTTagCompound nbt) {
        this.nbt = nbt;
    }
}
