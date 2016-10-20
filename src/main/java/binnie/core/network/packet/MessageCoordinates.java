// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import java.io.IOException;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChunkCoordinates;

public class MessageCoordinates extends MessageBase
{
	public int posX;
	public int posY;
	public int posZ;

	public MessageCoordinates(final MessageBinnie message) {
		super(message);
	}

	public MessageCoordinates(final int id, final ChunkCoordinates coordinates) {
		this(id, coordinates.posX, coordinates.posY, coordinates.posZ);
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

	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(this.posX, this.posY, this.posZ);
	}

	public TileEntity getTileEntity(final World world) {
		return world.getTileEntity(this.posX, this.posY, this.posZ);
	}
}
