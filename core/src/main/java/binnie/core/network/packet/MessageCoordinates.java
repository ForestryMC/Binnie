package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

public class MessageCoordinates extends MessageBase {
	private int posX;
	private int posY;
	private int posZ;

	public MessageCoordinates(MessageBinnie message) {
		super(message);
	}

	public MessageCoordinates(int id, BlockPos coordinates) {
		this(id, coordinates.getX(), coordinates.getY(), coordinates.getZ());
	}

	public MessageCoordinates(int id, int posX, int posY, int posZ) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		data.writeInt(this.posX);
		data.writeInt(this.posY);
		data.writeInt(this.posZ);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		this.posX = data.readInt();
		this.posY = data.readInt();
		this.posZ = data.readInt();
	}

	public BlockPos getCoordinates() {
		return new BlockPos(this.posX, this.posY, this.posZ);
	}

	@Nullable
	public TileEntity getTileEntity(World world) {
		return world.getTileEntity(getCoordinates());
	}
}
