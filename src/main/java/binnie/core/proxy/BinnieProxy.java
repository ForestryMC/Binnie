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

import java.io.File;

public class BinnieProxy extends BinnieModProxy implements IBinnieProxy {
	private short uniqueTextureUID;

	public BinnieProxy() {
		super(BinnieCore.instance);
		this.uniqueTextureUID = 1200;
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
	public void bindTexture(final BinnieResource texture) {
		// ignored
	}

	public boolean checkTexture(final BinnieResource location) {
		return false;
	}

	@Override
	public int getUniqueRenderID() {
		return RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void openGui(final AbstractMod mod, final int id, final EntityPlayer player, final int x, final int y, final int z) {
		player.openGui(mod, id, player.worldObj, x, y, z);
	}

	@Override
	public boolean isSimulating(final World world) {
		return true;
	}

	@Override
	public void registerCustomItemRenderer(final Item item, final IItemRenderer itemRenderer) {
	}

	@Override
	public boolean needsTagCompoundSynched(final Item item) {
		return item.getShareTag();
	}

	@Override
	public World getWorld() {
		return null;
	}

	// TODO unused method?
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
		// ignored
	}

	@Override
	public boolean isDebug() {
		return System.getenv().containsKey("BINNIE_DEBUG");
	}

	@Override
	public void registerBlockRenderer(final Object renderer) {
		// ignored
	}

	@Override
	public Object createObject(final String renderer) {
		return null;
	}

	public void sendNetworkEntityPacket(final INetworkedEntity entity) {
		MessageUpdate packet = new MessageUpdate(BinnieCorePacketID.NetworkEntityUpdate.ordinal(), entity);
		sendToAll(packet);
	}

	@Override
	public IIcon getIcon(final IIconRegister register, final String mod, final String name) {
		return null;
	}

	// TODO unused method?
	public void handleTextureRefresh(final IIconRegister register, final int type) {
		// ignored
	}

	// TODO unused method?
	public void handlePostTextureRefresh(final IIconRegister register, final int type) {
		// ignored
	}

	public short getUniqueTextureUID() {
		uniqueTextureUID++;
		return uniqueTextureUID;
	}

	@Override
	public void bindTexture(final ResourceLocation location) {
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
