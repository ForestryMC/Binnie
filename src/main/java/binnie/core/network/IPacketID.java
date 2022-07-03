package binnie.core.network;

import binnie.core.network.packet.MessageBinnie;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public interface IPacketID extends IOrdinaled {
    void onMessage(MessageBinnie message, MessageContext context);
}
