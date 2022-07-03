package binnie.core.proxy;

import binnie.Binnie;
import binnie.core.craftgui.resource.minecraft.CraftGUIResourceManager;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class BinnieProxyClient extends BinnieProxy implements IBinnieProxy {
    @Override
    public void bindTexture(BinnieResource texture) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bindTexture(texture.getResourceLocation());
    }

    @Override
    public void bindTexture(ResourceLocation location) {
        getMinecraftInstance().getTextureManager().bindTexture(location);
    }

    @Override
    public boolean checkTexture(BinnieResource location) {
        SimpleTexture texture = new SimpleTexture(location.getResourceLocation());
        try {
            texture.loadTexture(getMinecraftInstance().getResourceManager());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSimulating(World world) {
        return !world.isRemote;
    }

    @Override
    public void registerCustomItemRenderer(Item item, IItemRenderer itemRenderer) {
        MinecraftForgeClient.registerItemRenderer(item, itemRenderer);
    }

    @Override
    public World getWorld() {
        return getMinecraftInstance().theWorld;
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
    public void registerTileEntity(Class<? extends TileEntity> tile, String id, Object renderer) {
        if (renderer != null && renderer instanceof TileEntitySpecialRenderer) {
            ClientRegistry.registerTileEntity(tile, id, (TileEntitySpecialRenderer) renderer);
        } else {
            GameRegistry.registerTileEntity(tile, id);
        }
    }

    @Override
    public void registerBlockRenderer(Object renderer) {
        if (renderer != null && renderer instanceof ISimpleBlockRenderingHandler) {
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler) renderer);
        }
    }

    @Override
    public void createPipe(Item pipe) {
        // ignored
    }

    @Override
    public Object createObject(String renderer) {
        Object object = null;
        try {
            Class<?> rendererClass = Class.forName(renderer);
            if (rendererClass != null) {
                object = rendererClass.newInstance();
            }
        } catch (Exception ex) {
            // ignored
        }
        return object;
    }

    @Override
    public IIcon getIcon(IIconRegister register, String mod, String name) {
        return register.registerIcon(mod + ":" + name);
    }

    @Override
    public boolean isShiftDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    // TODO unused method?
    public void handlePreTextureRefresh(IIconRegister register, int type) {
        if (type == 0) {
            Binnie.Liquid.reloadIcons(register);
        }
    }

    @Override
    public void preInit() {
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        if (manager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) manager).registerReloadListener(new CraftGUIResourceManager());
        }
    }
}
