// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import binnie.core.network.packet.MessageBinnie;

public interface IPacketID extends IOrdinaled
{
	void onMessage(final MessageBinnie p0, final MessageContext p1);
}
