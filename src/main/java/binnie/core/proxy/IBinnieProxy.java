package binnie.core.proxy;

import binnie.core.*;
import binnie.core.resource.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.*;

import java.io.*;

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(BinnieResource texture);

	void bindTexture(ResourceLocation location);

	int getUniqueRenderID();

	void registerCustomItemRenderer(Item item, IItemRenderer itemRenderer);

	void openGui(AbstractMod mod, int id, EntityPlayer player, int x, int y, int z);

	boolean isSimulating(World world);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(Item item);

	Object createObject(String renderer);

	void registerTileEntity(Class<? extends TileEntity> tile, String id, Object renderer);

	void createPipe(Item pipe);

	boolean isDebug();

	void registerBlockRenderer(Object renderer);

	IIcon getIcon(IIconRegister register, String mod, String name);
}
