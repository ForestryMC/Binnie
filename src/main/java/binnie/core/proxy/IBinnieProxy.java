// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.proxy;

import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.AbstractMod;
import net.minecraftforge.client.IItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import binnie.core.resource.BinnieResource;
import java.io.File;

public interface IBinnieProxy extends IProxyCore
{
	boolean isClient();

	boolean isServer();

	File getDirectory();

	void bindTexture(final BinnieResource p0);

	void bindTexture(final ResourceLocation p0);

	int getUniqueRenderID();

	void registerCustomItemRenderer(final Item p0, final IItemRenderer p1);

	void openGui(final AbstractMod p0, final int p1, final EntityPlayer p2, final int p3, final int p4, final int p5);

	boolean isSimulating(final World p0);

	World getWorld();

	Minecraft getMinecraftInstance();

	boolean needsTagCompoundSynched(final Item p0);

	Object createObject(final String p0);

	void registerTileEntity(final Class<? extends TileEntity> p0, final String p1, final Object p2);

	void createPipe(final Item p0);

	boolean isDebug();

	void registerBlockRenderer(final Object p0);

	IIcon getIcon(final IIconRegister p0, final String p1, final String p2);
}
