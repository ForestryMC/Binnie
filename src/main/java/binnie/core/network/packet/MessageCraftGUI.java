package binnie.core.network.packet;

import binnie.core.network.*;
import net.minecraft.nbt.*;

public class MessageCraftGUI extends MessageNBT {
	public MessageCraftGUI(MessageBinnie message) {
		super(message);
	}

	public MessageCraftGUI(NBTTagCompound action) {
		super(BinnieCorePacketID.CraftGUIAction.ordinal(), action);
	}
}
