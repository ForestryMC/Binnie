package binnie.core.proxy;

import binnie.*;
import binnie.core.resource.*;
import binnie.craftgui.resource.minecraft.*;
import cpw.mods.fml.client.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

import java.io.*;

public class BinnieProxyClient extends BinnieProxy implements IBinnieProxy {
	@Override
	public void bindTexture(BinnieResource texture) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.bindTexture(texture.getResourceLocation());
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		this.getMinecraftInstance().getTextureManager().bindTexture(location);
	}

	@Override
	public boolean checkTexture(BinnieResource location) {
		SimpleTexture texture = new SimpleTexture(location.getResourceLocation());
		try {
			texture.loadTexture(this.getMinecraftInstance().getResourceManager());
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
