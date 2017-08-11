package binnie.core.proxy;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.gui.resource.minecraft.CraftGUIResourceManager;
import binnie.core.models.ModelManager;
import binnie.core.resource.BinnieResource;

@SideOnly(Side.CLIENT)
public final class BinnieProxyClient extends BinnieProxy implements IBinnieProxy {
	public static ModelManager modelManager = new ModelManager(Constants.CORE_MOD_ID);

	public static ModelManager getModelManager() {
		return modelManager;
	}

	@Override
	public Item registerItem(Item item) {
		getModelManager().registerItemClient(item);
		return super.registerItem(item);
	}

	@Override
	public Block registerBlock(Block block) {
		getModelManager().registerBlockClient(block);
		return super.registerBlock(block);
	}

	@Override
	public void registerModels() {
		getModelManager().registerModels();
	}

	@Override
	public void reloadSprites() {
		ModelManager.reloadSprites();
	}

	@Override
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
	}

	@Override
	public void registerSprite(ResourceLocation location) {
		Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(location);
	}

	@Override
	public void registerModel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	@Override
	public void bindTexture(final BinnieResource texture) {
		this.bindTexture(texture.getResourceLocation());
	}

	@Override
	public void bindTexture(final ResourceLocation location) {
		this.getMinecraftInstance().getTextureManager().bindTexture(location);
	}

	@Override
	public boolean checkTexture(final BinnieResource location) {
		final SimpleTexture texture = new SimpleTexture(location.getResourceLocation());
		try {
			texture.loadTexture(this.getMinecraftInstance().getResourceManager());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public World getWorld() {
		return this.getMinecraftInstance().world;
	}

	@Override
	public Minecraft getMinecraftInstance() {
		return FMLClientHandler.instance().getClient();
	}

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isServer() {
		return false;
	}

	@Override
	public File getDirectory() {
		return new File(".");
	}

	@Override
	public void registerTileEntity(final Class<? extends TileEntity> tile, final String id, @Nullable final Object renderer) {
		if (renderer != null && renderer instanceof TileEntitySpecialRenderer) {
			ClientRegistry.registerTileEntity(tile, id, (TileEntitySpecialRenderer) renderer);
		} else {
			GameRegistry.registerTileEntity(tile, id);
		}
	}

	@Override
	public void registerBlockRenderer(final Object renderer) {
		//if (renderer != null && renderer instanceof FastTESR) {
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.class, new RendererMachine());//.registerBlockHandler((ISimpleBlockRenderingHandler) renderer);
		//}
	}

	@Override
	public void createPipe(final Item pipe) {
	}

	@Override
	public TextureAtlasSprite getTextureAtlasSprite(ResourceLocation location) {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
	}

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public void preInit() {
		final IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
		if (manager instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) manager).registerReloadListener(new CraftGUIResourceManager());
		}
	}
}
