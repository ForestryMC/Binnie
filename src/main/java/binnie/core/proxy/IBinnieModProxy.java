// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.proxy;

import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.core.network.packet.MessageBase;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.gui.IBinnieGUID;

interface IBinnieModProxy extends IProxyCore
{
	void openGui(final IBinnieGUID p0, final EntityPlayer p1, final int p2, final int p3, final int p4);

	void sendToAll(final MessageBase p0);

	void sendToPlayer(final MessageBase p0, final EntityPlayer p1);

	void sendToServer(final MessageBase p0);

	IIcon getIcon(final IIconRegister p0, final String p1);
}
