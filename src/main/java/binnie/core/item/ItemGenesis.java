package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGenesis extends Item {
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
    }

    public ItemGenesis() {
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("genesis");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        BinnieCore.proxy.openGui(
                BinnieCoreGUI.Genesis, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return itemStack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        return I18N.localise("binniecore.item.genesis.name");
    }
}
