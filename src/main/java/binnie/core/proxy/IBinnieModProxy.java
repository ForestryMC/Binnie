package binnie.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;

public interface IBinnieModProxy extends IProxyCore {
	void openGui(final IBinnieGUID p0, final EntityPlayer p1, final BlockPos pos);

	void sendToAll(final MessageBase p0);

	void sendToPlayer(final MessageBase p0, final EntityPlayer p1);

	void sendToServer(final MessageBase p0);

	//IIcon getIcon(final IIconRegister p0, final String p1);
}
