package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageUpdate;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class BinnieProxy extends BinnieModProxy implements IBinnieProxy {
    private short uniqueTextureUID;

    public BinnieProxy() {
        super(BinnieCore.instance);
        uniqueTextureUID = 1200;
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

    @Override
    public void bindTexture(BinnieResource texture) {
        // ignored
    }

    public boolean checkTexture(BinnieResource location) {
        return false;
    }

    @Override
    public int getUniqueRenderID() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void openGui(AbstractMod mod, int id, EntityPlayer player, int x, int y, int z) {
        player.openGui(mod, id, player.worldObj, x, y, z);
    }

    @Override
    public boolean isSimulating(World world) {
        return true;
    }

    @Override
    public void registerCustomItemRenderer(Item item, IItemRenderer itemRenderer) {}

    @Override
    public boolean needsTagCompoundSynched(Item item) {
        return item.getShareTag();
    }

    @Override
    public World getWorld() {
        return null;
    }

    // TODO unused method?
    public void throwException(String message, Throwable e) {
        FMLCommonHandler.instance().raiseException(e, message, true);
    }

    @Override
    public Minecraft getMinecraftInstance() {
        return null;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public boolean isServer() {
        return true;
    }

    @Override
    public File getDirectory() {
        return new File("./");
    }

    @Override
    public void registerTileEntity(Class<? extends TileEntity> tile, String id, Object renderer) {
        GameRegistry.registerTileEntity(tile, id);
    }

    @Override
    public void createPipe(Item pipe) {
        // ignored
    }

    @Override
    public boolean isDebug() {
        return System.getenv().containsKey("BINNIE_DEBUG");
    }

    @Override
    public void registerBlockRenderer(Object renderer) {
        // ignored
    }

    @Override
    public Object createObject(String renderer) {
        return null;
    }

    public void sendNetworkEntityPacket(INetworkedEntity entity) {
        MessageUpdate packet = new MessageUpdate(BinnieCorePacketID.NetworkEntityUpdate.ordinal(), entity);
        sendToAll(packet);
    }

    @Override
    public IIcon getIcon(IIconRegister register, String mod, String name) {
        return null;
    }

    // TODO unused method?
    public void handleTextureRefresh(IIconRegister register, int type) {
        // ignored
    }

    // TODO unused method?
    public void handlePostTextureRefresh(IIconRegister register, int type) {
        // ignored
    }

    public short getUniqueTextureUID() {
        uniqueTextureUID++;
        return uniqueTextureUID;
    }

    @Override
    public void bindTexture(ResourceLocation location) {
        // ignored
    }

    public boolean isShiftDown() {
        return false;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public MinecraftServer getServer() {
        return MinecraftServer.getServer();
    }
}
