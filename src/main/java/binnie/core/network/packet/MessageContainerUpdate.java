package binnie.core.network.packet;

import net.minecraft.nbt.NBTTagCompound;

public class MessageContainerUpdate extends MessageCraftGUI {
	public MessageContainerUpdate(NBTTagCompound nbt) {
		super(nbt);
	}

	// TODO unused constructor?
	public MessageContainerUpdate(MessageBinnie message) {
		super(message);
	}
}
