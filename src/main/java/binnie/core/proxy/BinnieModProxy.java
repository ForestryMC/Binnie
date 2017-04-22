package binnie.core.proxy;

import binnie.*;
import binnie.core.*;
import binnie.core.gui.*;
import binnie.core.network.packet.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BinnieModProxy implements IBinnieModProxy {
	private AbstractMod mod;

	public BinnieModProxy(AbstractMod mod) {
		this.mod = mod;
	}

	@Override
	public void openGui(IBinnieGUID ID, EntityPlayer player, int x, int y, int z) {
		BinnieCore.proxy.openGui(mod, ID.ordinal(), player, x, y, z);
	}

	@Override
	public void sendToAll(MessageBase packet) {
		mod.getNetworkWrapper().sendToAll(packet.getMessage());
	}

	@Override
	public void sendToPlayer(MessageBase packet, EntityPlayer entityplayer) {
		if (entityplayer instanceof EntityPlayerMP) {
			mod.getNetworkWrapper().sendTo(packet.getMessage(), (EntityPlayerMP) entityplayer);
		}
	}

	@Override
	public void sendToServer(MessageBase packet) {
		mod.getNetworkWrapper().sendToServer(packet.getMessage());
	}

	@Override
	public IIcon getIcon(IIconRegister register, String string) {
		return BinnieCore.proxy.getIcon(register, mod.getModID(), string);
	}

	@Override
	public void preInit() {
		// ignored
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		// ignored
	}

	public String localise(String string) {
		return Binnie.Language.localise(mod, string);
	}

	public String localiseOrBlank(String string) {
		return Binnie.Language.localiseOrBlank(mod, string);
	}
}
