package binnie.core.proxy;

import binnie.Constants;
import binnie.core.models.ModelManager;
import binnie.core.resource.BinnieResource;
import binnie.craftgui.resource.minecraft.CraftGUIResourceManager;
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
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;

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
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
	}

    @Override
    public void registerIcon(ResourceLocation location) {
        Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(location);
    }

    @Override
    public void registermodel(Item item, int meta) {
        registermodel(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void registermodel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
    }

    @Override
    public void bindTexture(final BinnieResource texture) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
    public boolean isSimulating(final World world) {
        return !world.isRemote;
    }

//	@Override
//	public void registerCustomItemRenderer(final Item item, final IItemRenderer itemRenderer) {
//		MinecraftForgeClient.registerItemRenderer(item, itemRenderer);
//	}

    @Override
    public World getWorld() {
        return this.getMinecraftInstance().theWorld;
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
    public void registerTileEntity(final Class<? extends TileEntity> tile, final String id, final Object renderer) {
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
    public Object createObject(final String renderer) {
        Object object = null;
        try {
            final Class<?> rendererClass = Class.forName(renderer);
            if (rendererClass != null) {
                object = rendererClass.newInstance();
            }
        } catch (Exception ex) {
        }
        return object;
    }

//	@Override
//	public IIcon getIcon(final IIconRegister register, final String mod, final String name) {
//		return register.registerIcon(mod + ":" + name);
//	}

    @Override
    public TextureAtlasSprite getTextureAtlasSprite(ResourceLocation location) {
    	if(location == null){
    		return null;
    	}
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    @Override
    public boolean isShiftDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

//	public void handlePreTextureRefresh(final IIconRegister register, final int type) {
//		if (type == 0) {
//			Binnie.Liquid.reloadIcons(register);
//		}
//	}

    @Override
    public void preInit() {
        final IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        if (manager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) manager).registerReloadListener(new CraftGUIResourceManager());
        }
    }
}
