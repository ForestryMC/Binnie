package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IIcon;

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
}
