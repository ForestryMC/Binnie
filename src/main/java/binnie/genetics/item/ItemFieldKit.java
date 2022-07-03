package binnie.genetics.item;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.util.I18N;
import binnie.genetics.GeneticsCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFieldKit extends Item {
    private IIcon fieldKit0;
    private IIcon fieldKit1;
    private IIcon fieldKit2;
    private IIcon fieldKit3;

    public ItemFieldKit() {
        setUnlocalizedName("fieldKit");
        setCreativeTab(GeneticsCreativeTab.instance);
        setMaxStackSize(1);
        setMaxDamage(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        fieldKit0 = Botany.proxy.getIcon(register, "fieldKit");
        fieldKit1 = Botany.proxy.getIcon(register, "fieldKit1");
        fieldKit2 = Botany.proxy.getIcon(register, "fieldKit2");
        fieldKit3 = Botany.proxy.getIcon(register, "fieldKit3");
        itemIcon = fieldKit0;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        int damage = stack.getItemDamage();
        if (damage < 24) {
            return fieldKit3;
        }
        if (damage < 48) {
            return fieldKit2;
        }
        if (damage < 64) {
            return fieldKit1;
        }
        return fieldKit0;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!player.isSneaking()) {
            BinnieCore.proxy.openGui(
                    BinnieCoreGUI.FieldKit, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
        int i = getMaxDamage() - itemStack.getItemDamage();
        if (i == 0) {
            tooltip.add(I18N.localise("genetics.item.fieldKit.tooltip.noPaper"));
        } else {
            if (i > 1) {
                tooltip.add(I18N.localise("genetics.item.fieldKit.tooltip.sheetsOfPaper", i));
            } else {
                tooltip.add(I18N.localise("genetics.item.fieldKit.tooltip.sheetOfPaper", i));
            }
        }
        super.addInformation(itemStack, player, tooltip, advanced);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18N.localise("genetics.item.fieldKit.name");
    }
}
