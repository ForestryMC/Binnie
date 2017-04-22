package binnie.core.network;

import binnie.core.network.packet.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public interface IPacketID extends IOrdinaled {
	void onMessage(MessageBinnie message, MessageContext context);
}
