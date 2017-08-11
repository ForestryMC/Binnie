package binnie.core.proxy;

import javax.annotation.Nullable;
import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import binnie.core.AbstractMod;
import binnie.core.resource.BinnieResource;

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(final BinnieResource p0);

	void bindTexture(final ResourceLocation p0);

	//int getUniqueRenderID();

	//void registerCustomItemRenderer(final Item p0, final IItemRenderer p1);

	void openGui(final AbstractMod p0, final int p1, final EntityPlayer p2, final BlockPos pos);

	World getWorld();

	//EntityPlayer getPlayer();

	//MinecraftServer getServer();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(final Item p0);

	void registerTileEntity(final Class<? extends TileEntity> tile, final String id, @Nullable final Object renderer);

	void registerTileEntity(final Class<? extends TileEntity> tile, final String id);

	void createPipe(final Item p0);

	boolean isDebug();

	void registerBlockRenderer(final Object p0);
}
