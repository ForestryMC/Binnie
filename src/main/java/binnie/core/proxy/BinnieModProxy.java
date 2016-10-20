// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.proxy;

import binnie.Binnie;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import binnie.core.network.packet.MessageBase;
import binnie.core.BinnieCore;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.gui.IBinnieGUID;
import binnie.core.AbstractMod;

public class BinnieModProxy implements IBinnieModProxy
{
	private AbstractMod mod;

	public BinnieModProxy(final AbstractMod mod) {
		this.mod = mod;
	}

	@Override
	public void openGui(final IBinnieGUID ID, final EntityPlayer player, final int x, final int y, final int z) {
		BinnieCore.proxy.openGui(this.mod, ID.ordinal(), player, x, y, z);
	}

	@Override
	public void sendToAll(final MessageBase packet) {
		this.mod.getNetworkWrapper().sendToAll(packet.GetMessage());
	}

	@Override
	public void sendToPlayer(final MessageBase packet, final EntityPlayer entityplayer) {
		if (entityplayer instanceof EntityPlayerMP) {
			this.mod.getNetworkWrapper().sendTo((IMessage) packet.GetMessage(), (EntityPlayerMP) entityplayer);
		}
	}

	@Override
	public void sendToServer(final MessageBase packet) {
		this.mod.getNetworkWrapper().sendToServer(packet.GetMessage());
	}

	@Override
	public IIcon getIcon(final IIconRegister register, final String string) {
		return BinnieCore.proxy.getIcon(register, this.mod.getModID(), string);
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	public String localise(final String string) {
		return Binnie.Language.localise(this.mod, string);
	}

	public String localiseOrBlank(final String string) {
		return Binnie.Language.localiseOrBlank(this.mod, string);
	}
}
