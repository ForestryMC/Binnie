package binnie.core.proxy;

import binnie.core.AbstractMod;
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

import java.io.File;
import java.util.function.Supplier;

public interface IBinnieProxy extends IProxyCore {
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(ResourceLocation location);

	void openGui(final AbstractMod p0, final int p1, final EntityPlayer p2, final BlockPos pos);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(Item item);

	void registerTileEntity(final Class<? extends TileEntity> tile, final ResourceLocation location);

	<T extends TileEntity> void registerTileEntity(Class<? extends T> tile, ResourceLocation location, ClientSupplier<TileEntitySpecialRenderer<T>> rendererSupplier);

	interface ClientSupplier<T> extends Supplier<T> {
		@SideOnly(Side.CLIENT)
		@Override
		T get();
	}
}
