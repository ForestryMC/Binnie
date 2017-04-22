package binnie.core.proxy;

import binnie.core.*;
import binnie.core.network.*;
import binnie.core.network.packet.*;
import binnie.core.resource.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.*;

import java.io.*;

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
	public void registerCustomItemRenderer(Item item, IItemRenderer itemRenderer) {
	}

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
