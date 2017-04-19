package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.resource.BinnieResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import java.io.File;

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(final BinnieResource texture);

	void bindTexture(final ResourceLocation location);

	int getUniqueRenderID();

	void registerCustomItemRenderer(final Item item, final IItemRenderer itemRenderer);

	void openGui(final AbstractMod mod, final int id, final EntityPlayer player, final int x, final int y, final int z);

	boolean isSimulating(final World world);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(final Item item);

	Object createObject(final String renderer);

	void registerTileEntity(final Class<? extends TileEntity> tile, final String id, final Object renderer);

	void createPipe(final Item pipe);

	boolean isDebug();

	void registerBlockRenderer(final Object renderer);

	IIcon getIcon(final IIconRegister register, final String mod, final String name);
}
