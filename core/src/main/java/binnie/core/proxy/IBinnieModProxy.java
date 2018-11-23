package binnie.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;

public interface IBinnieModProxy extends IProxyCore {
	void openGui(IBinnieGUID p0, EntityPlayer p1, BlockPos pos);

	void sendToAll(MessageBase p0);

	void sendToPlayer(MessageBase p0, EntityPlayer p1);

	void sendToServer(MessageBase p0);
}
