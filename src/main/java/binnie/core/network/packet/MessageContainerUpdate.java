package binnie.core.network.packet;

import net.minecraft.nbt.*;

public class MessageContainerUpdate extends MessageCraftGUI {
	public MessageContainerUpdate(NBTTagCompound nbt) {
		super(nbt);
	}

	// TODO unused constructor?
	public MessageContainerUpdate(MessageBinnie message) {
		super(message);
	}
}
