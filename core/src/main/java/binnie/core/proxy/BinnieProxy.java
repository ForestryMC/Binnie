package binnie.core.proxy;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import binnie.core.AbstractMod;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageUpdate;
import binnie.core.liquid.FluidType;

public abstract class BinnieProxy extends BinnieModProxy implements IBinnieProxy {
	private short uniqueTextureUID;

	public BinnieProxy() {
		this.uniqueTextureUID = 1200;
	}

	public boolean checkTexture(final ResourceLocation location) {
		return false;
	}

	@Override
	public void openGui(final AbstractMod mod, final int id, final EntityPlayer player, final BlockPos pos) {
		player.openGui(mod.getMod(), id, player.world, pos.getX(), pos.getY(), pos.getZ());
	}

	public TextureAtlasSprite getTextureAtlasSprite(ResourceLocation location) {
		throw new UnsupportedOperationException("Cannot call getTextureAtlasSprite on server side");
	}

	@Override
	public boolean needsTagCompoundSynched(final Item item) {
		return item.getShareTag();
	}

	@Override
	public World getWorld() {
		return getServer().getEntityWorld();
	}

	@Override
	public Minecraft getMinecraftInstance() {
		throw new UnsupportedOperationException("Cannot call getMinecraftInstance on server side");
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
	public <T extends TileEntity> void registerTileEntity(Class<? extends T> tile, String id, ClientSupplier<TileEntitySpecialRenderer<T>> rendererSupplier) {
		registerTileEntity(tile, id);
	}

	@Override
	public void registerTileEntity(final Class<? extends TileEntity> tile, final String id) {
		GameRegistry.registerTileEntity(tile, id);
	}

	public void sendNetworkEntityPacket(final INetworkedEntity entity) {
		final MessageUpdate packet = new MessageUpdate(BinnieCorePacketID.NETWORK_ENTITY_UPDATE.ordinal(), entity);
		this.sendToAll(packet);
	}

	public void registerSprite(ResourceLocation location) {

	}

	public void registerFluidStateMapper(Block block, FluidType fluid) {
	}

	public short getUniqueTextureUID() {
		final short uniqueTextureUID = this.uniqueTextureUID;
		this.uniqueTextureUID = (short) (uniqueTextureUID + 1);
		return uniqueTextureUID;
	}

	/**
	 * Binds GL to this texture location.
	 */
	@Override
	public void bindTexture(final ResourceLocation location) {
	}

	/**
	 * Reloads the sprites on the client side.
	 */
	public void reloadSprites() {
	}

	public EntityPlayer getPlayer() {
		throw new UnsupportedOperationException("Cannot call getPlayer on server side");
	}

	public MinecraftServer getServer() {
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
}
