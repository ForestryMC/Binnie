package binnie.core.network;

import binnie.core.network.packet.MessageBinnie;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IPacketID extends IOrdinaled {
    void onMessage(final MessageBinnie p0, final MessageContext p1);
}
