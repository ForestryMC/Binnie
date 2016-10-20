package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.item.IColoredItem;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageUpdate;
import binnie.core.resource.BinnieResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

public class BinnieProxy extends BinnieModProxy implements IBinnieProxy {
    private short uniqueTextureUID;


    public BinnieProxy() {
        super(BinnieCore.instance);
        this.uniqueTextureUID = 1200;
    }

    public void registerItemColors(IColoredItem itemColor) {

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

    @Override
    public void bindTexture(final BinnieResource texture) {
    }

    public boolean checkTexture(final BinnieResource location) {
        return false;
    }

//	@Override
//	public int getUniqueRenderID() {
//		return RenderingRegistry.getNextAvailableRenderId();
//	}

    @Override
    public void openGui(final AbstractMod mod, final int id, final EntityPlayer player, final BlockPos pos) {
        player.openGui(mod, id, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public boolean isSimulating(final World world) {
        return true;
    }

//	@Override
//	public void registerCustomItemRenderer(final Item item, final IItemRenderer itemRenderer) {
//	}

    public TextureAtlasSprite getTextureAtlasSprite(ResourceLocation location) {
        return null;
    }

    @Override
    public boolean needsTagCompoundSynched(final Item item) {
        return item.getShareTag();
    }

    @Override
    public World getWorld() {
        return null;
    }

    public void throwException(final String message, final Throwable e) {
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
    public void registerTileEntity(final Class<? extends TileEntity> tile, final String id, final Object renderer) {
        GameRegistry.registerTileEntity(tile, id);
    }

    @Override
    public void createPipe(final Item pipe) {
    }

    @Override
    public boolean isDebug() {
        return System.getenv().containsKey("BINNIE_DEBUG");
    }

    @Override
    public void registerBlockRenderer(final Object renderer) {
    }

    @Override
    public Object createObject(final String renderer) {
        return null;
    }

    public void sendNetworkEntityPacket(final INetworkedEntity entity) {
        final MessageUpdate packet = new MessageUpdate(BinnieCorePacketID.NetworkEntityUpdate.ordinal(), entity);
        this.sendToAll(packet);
    }

    public void registerIcon(ResourceLocation location) {

    }

//	@Override
//	public IIcon getIcon(final IIconRegister register, final String mod, final String name) {
//		return null;
//	}
//
//	public void handleTextureRefresh(final IIconRegister register, final int type) {
//	}
//
//	public void handlePostTextureRefresh(final IIconRegister register, final int type) {
//	}

    public short getUniqueTextureUID() {
        final short uniqueTextureUID = this.uniqueTextureUID;
        this.uniqueTextureUID = (short) (uniqueTextureUID + 1);
        return uniqueTextureUID;
    }

    @Override
    public void bindTexture(final ResourceLocation location) {
    }

    public boolean isShiftDown() {
        return false;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}
