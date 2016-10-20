package binnie.core.network.packet;

import binnie.core.network.BinnieCorePacketID;
import net.minecraft.nbt.NBTTagCompound;

public class MessageCraftGUI extends MessageNBT {
    public MessageCraftGUI(final MessageBinnie message) {
        super(message);
    }

    public MessageCraftGUI(final NBTTagCompound action) {
        super(BinnieCorePacketID.CraftGUIAction.ordinal(), action);
    }
}
