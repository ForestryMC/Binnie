package binnie.core.network.packet;

import io.netty.buffer.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.io.*;

public class MessageCoordinates extends MessageBase {
	public int posX;
	public int posY;
	public int posZ;

	public MessageCoordinates(MessageBinnie message) {
		super(message);
	}

	// TODO unused constructor?
	public MessageCoordinates(int id, ChunkCoordinates coordinates) {
		this(id, coordinates.posX, coordinates.posY, coordinates.posZ);
	}

	public MessageCoordinates(int id, int posX, int posY, int posZ) {
		super(id);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
	}

	// TODO unused method?
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(posX, posY, posZ);
	}

	public TileEntity getTileEntity(World world) {
		return world.getTileEntity(posX, posY, posZ);
	}
}
