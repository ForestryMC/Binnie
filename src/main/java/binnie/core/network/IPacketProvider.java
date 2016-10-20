// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network;

public interface IPacketProvider
{
	String getChannel();

	IPacketID[] getPacketIDs();
}
