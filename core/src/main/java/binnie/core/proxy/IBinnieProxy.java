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

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(ResourceLocation location);

	void openGui(final AbstractMod p0, final int p1, final EntityPlayer p2, final BlockPos pos);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(Item item);

	void registerTileEntity(final Class<? extends TileEntity> tile, final String id, @Nullable final Object renderer);

	void registerTileEntity(final Class<? extends TileEntity> tile, final String id);
}
