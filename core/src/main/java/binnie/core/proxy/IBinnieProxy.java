package binnie.core.proxy;

import java.io.File;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(ResourceLocation location);

	void openGui(AbstractMod p0, int p1, EntityPlayer p2, BlockPos pos);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(Item item);

	void registerTileEntity(Class<? extends TileEntity> tile, String id);

	<T extends TileEntity> void registerTileEntity(Class<? extends T> tile, String id, ClientSupplier<TileEntitySpecialRenderer<T>> rendererSupplier);

	interface ClientSupplier<T> extends Supplier<T> {
		@SideOnly(Side.CLIENT)
		@Override
		T get();
	}
}
