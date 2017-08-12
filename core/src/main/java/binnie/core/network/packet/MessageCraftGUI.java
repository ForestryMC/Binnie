package binnie.core.network.packet;

import net.minecraft.nbt.NBTTagCompound;

import binnie.core.network.BinnieCorePacketID;

public class MessageCraftGUI extends MessageNBT {
	public MessageCraftGUI(final MessageBinnie message) {
		super(message);
	}

	public MessageCraftGUI(final NBTTagCompound action) {
		super(BinnieCorePacketID.CRAFT_GUI_ACTION.ordinal(), action);
	}
}
