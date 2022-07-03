package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.resource.BinnieResource;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

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
