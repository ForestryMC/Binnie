package binnie.core.proxy;

import binnie.core.gui.*;
import binnie.core.network.packet.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

interface IBinnieModProxy extends IProxyCore {
	void openGui(IBinnieGUID mod, EntityPlayer player, int x, int y, int z);

	void sendToAll(MessageBase p0);

	void sendToPlayer(MessageBase p0, EntityPlayer p1);

	void sendToServer(MessageBase p0);

	IIcon getIcon(IIconRegister p0, String p1);
}
