package binnie.core.proxy;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.gui.KeyBindings;
import binnie.core.gui.resource.stylesheet.StyleSheetManager;
import binnie.core.liquid.FluidType;
import binnie.core.models.ModelManager;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public final class BinnieProxyClient extends BinnieProxy implements IBinnieProxy {
	private static final ModelManager MODEL_MANAGER = new ModelManager(Constants.CORE_MOD_ID);

	public static ModelManager getModelManager() {
		return MODEL_MANAGER;
	}

	@Override
	public void onRegisterBlock(Block block) {
		getModelManager().registerBlockClient(block);
		super.onRegisterBlock(block);
	}

	@Override
	public void onRegisterItem(Item item) {
		getModelManager().registerItemClient(item);
		super.onRegisterItem(item);
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
	public void registerFluidStateMapper(Block block, FluidType fluid) {
		final ModelResourceLocation fluidLocation = new ModelResourceLocation("binniecore:blockbinniefluid", fluid.getIdentifier());
		StateMapperBase ignoreState = new FluidStateMapper(fluidLocation);
		ModelLoader.setCustomStateMapper(block, ignoreState);
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new FluidItemMeshDefinition(fluidLocation));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(block), fluidLocation);
	}

	private static class FluidStateMapper extends StateMapperBase {
		private final ModelResourceLocation fluidLocation;

		public FluidStateMapper(ModelResourceLocation fluidLocation) {
			this.fluidLocation = fluidLocation;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
			return fluidLocation;
		}
	}

	private static class FluidItemMeshDefinition implements ItemMeshDefinition {
		private final ModelResourceLocation fluidLocation;

		public FluidItemMeshDefinition(ModelResourceLocation fluidLocation) {
			this.fluidLocation = fluidLocation;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return fluidLocation;
		}
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
	public void bindTexture(final ResourceLocation location) {
		this.getMinecraftInstance().getTextureManager().bindTexture(location);
	}

	@Override
	public boolean checkTexture(final ResourceLocation location) {
		final SimpleTexture texture = new SimpleTexture(location);
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
	public <T extends TileEntity> void registerTileEntity(Class<? extends T> tile, final String id, ClientSupplier<TileEntitySpecialRenderer<T>> rendererSupplier) {
		ClientRegistry.registerTileEntity(tile, id, rendererSupplier.get());
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
			IReloadableResourceManager resourceManager = (IReloadableResourceManager) manager;
			resourceManager.registerReloadListener(new StyleSheetManager());
			resourceManager.registerReloadListener(resourceManager1 -> I18N.onResourceReload());
		}
	}

	@Override
	public void init() {
		super.init();
		KeyBindings.init();
	}
}
