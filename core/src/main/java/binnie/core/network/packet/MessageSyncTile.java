package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

public class MessageSyncTile extends MessageNBT {
	private int posX;
	private int posY;
	private int posZ;

	public MessageSyncTile(final MessageBinnie message) {
		super(message);
	}

	public MessageSyncTile(final int id, final TileEntity tile, final NBTTagCompound nbt) {
		super(id);
		this.posX = tile.getPos().getX();
		this.posY = tile.getPos().getY();
		this.posZ = tile.getPos().getZ();
		this.nbt = nbt;
	}

	@Override
	public void writeData(final ByteBuf data) throws IOException {
		data.writeInt(this.posX);
		data.writeInt(this.posY);
		data.writeInt(this.posZ);
		super.writeData(data);
	}

	@Override
	public void readData(final ByteBuf data) throws IOException {
		this.posX = data.readInt();
		this.posY = data.readInt();
		this.posZ = data.readInt();
		super.readData(data);
	}

	@Nullable
	public TileEntity getTarget(final World world) {
		return world.getTileEntity(new BlockPos(this.posX, this.posY, this.posZ));
	}

	public int getX() {
		return this.posX;
	}

	public int getY() {
		return this.posY;
	}

	public int getZ() {
		return this.posZ;
	}

	@Override
	public NBTTagCompound getTagCompound() {
		return this.nbt;
	}

	@Override
	void setTagCompound(final NBTTagCompound nbt) {
		this.nbt = nbt;
	}
}
