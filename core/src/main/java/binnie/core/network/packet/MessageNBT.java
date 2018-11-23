package binnie.core.network.packet;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import io.netty.buffer.ByteBuf;

public class MessageNBT extends MessageBase {
	@Nullable
	private NBTTagCompound nbt;

	public MessageNBT(int id) {
		super(id);
	}

	public MessageNBT(int id, NBTTagCompound nbt) {
		this(id);
		this.setTagCompound(nbt);
	}

	public MessageNBT(MessageBinnie message) {
		super(message);
	}

	@Nullable
	public NBTTagCompound getTagCompound() {
		return this.nbt;
	}

	void setTagCompound(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		this.writeNBTTagCompound(this.nbt, data);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		this.nbt = this.readNBTTagCompound(data);
	}
}
