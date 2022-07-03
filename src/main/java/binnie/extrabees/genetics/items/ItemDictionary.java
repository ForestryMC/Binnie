package binnie.extrabees.genetics.items;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDictionary extends Item {
    protected IIcon iconMaster;

    public ItemDictionary() {
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("dictionary");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = ExtraBees.proxy.getIcon(register, "apiaristDatabase");
        iconMaster = ExtraBees.proxy.getIcon(register, "masterApiaristDatabase");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return (damage == 0) ? itemIcon : iconMaster;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (stack.getItemDamage() > 0) {
            tooltip.add(I18N.localise("extrabees.item.database.tooltip"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        super.getSubItems(item, tab, list);
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 0) {
            ExtraBees.proxy.openGui(
                    ExtraBeeGUID.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            ExtraBees.proxy.openGui(
                    ExtraBeeGUID.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return (stack.getItemDamage() == 0)
                ? I18N.localise("extrabees.item.database")
                : I18N.localise("extrabees.item.masterDatabase");
    }
}
