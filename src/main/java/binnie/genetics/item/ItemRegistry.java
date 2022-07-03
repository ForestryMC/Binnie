package binnie.genetics.item;

import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
import binnie.genetics.core.GeneticsGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRegistry extends Item {
    public ItemRegistry() {
        setCreativeTab(GeneticsCreativeTab.instance);
        setUnlocalizedName("registry");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Genetics.proxy.getIcon(register, "registry");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        Genetics.proxy.openGui(GeneticsGUI.Registry, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack i) {
        return I18N.localise("genetics.item.registry.0.name");
    }
}
