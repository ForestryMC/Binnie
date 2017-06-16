package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

public class MessageCoordinates extends MessageBase {
	public int posX;
	public int posY;
	public int posZ;

	public MessageCoordinates(final MessageBinnie message) {
		super(message);
	}

	public MessageCoordinates(final int id, final BlockPos coordinates) {
		this(id, coordinates.getX(), coordinates.getY(), coordinates.getZ());
	}

	public MessageCoordinates(final int id, final int posX, final int posY, final int posZ) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void writeData(final ByteBuf data) throws IOException {
		data.writeInt(this.posX);
		data.writeInt(this.posY);
		data.writeInt(this.posZ);
	}

	@Override
	public void readData(final ByteBuf data) throws IOException {
		this.posX = data.readInt();
		this.posY = data.readInt();
		this.posZ = data.readInt();
	}

	public BlockPos getCoordinates() {
		return new BlockPos(this.posX, this.posY, this.posZ);
	}

	@Nullable
	public TileEntity getTileEntity(final World world) {
		return world.getTileEntity(getCoordinates());
	}
}
