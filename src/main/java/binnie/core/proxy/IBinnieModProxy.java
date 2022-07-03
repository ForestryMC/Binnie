package binnie.core.proxy;

import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;

interface IBinnieModProxy extends IProxyCore {
    void openGui(IBinnieGUID mod, EntityPlayer player, int x, int y, int z);

    void sendToAll(MessageBase p0);

    void sendToPlayer(MessageBase p0, EntityPlayer p1);

    void sendToServer(MessageBase p0);

    IIcon getIcon(IIconRegister p0, String p1);
}
