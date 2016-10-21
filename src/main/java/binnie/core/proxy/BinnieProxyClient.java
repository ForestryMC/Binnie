package binnie.core.proxy;

import binnie.core.item.IColoredItem;
import binnie.core.machines.RendererMachine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.craftgui.resource.minecraft.CraftGUIResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.ITextureObject;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;

public final class BinnieProxyClient extends BinnieProxy implements IBinnieProxy {
    public void registerItemColors(IColoredItem itemColor) {
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        if (itemColor instanceof Item) {
            itemColors.registerItemColorHandler(ColoredItemItemColor.INSTANCE, (Item) itemColor);
        }
    }

    @Override
    public void registerIcon(ResourceLocation location) {
        Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(location);
    }

    @SideOnly(Side.CLIENT)
    private static class ColoredItemItemColor implements IItemColor {
        public static final ColoredItemItemColor INSTANCE = new ColoredItemItemColor();

        private ColoredItemItemColor() {

        }

        @Override
        public int getColorFromItemstack(ItemStack stack, int tintIndex) {
            Item item = stack.getItem();
            if (item instanceof IColoredItem) {
                return ((IColoredItem) item).getColorFromItemstack(stack, tintIndex);
            }
            return 0xffffff;
        }
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.class, new RendererMachine());//.registerBlockHandler((ISimpleBlockRenderingHandler) renderer);
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
