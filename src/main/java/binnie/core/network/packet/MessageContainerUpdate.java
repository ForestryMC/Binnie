package binnie.core.network.packet;

import net.minecraft.nbt.NBTTagCompound;

public class MessageContainerUpdate extends MessageCraftGUI {
    public MessageContainerUpdate(final NBTTagCompound nbt) {
        super(nbt);
    }

    public MessageContainerUpdate(final MessageBinnie message) {
        super(message);
    }
}
