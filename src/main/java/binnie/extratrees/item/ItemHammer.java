package binnie.extratrees.item;

import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHammer extends Item implements IToolHammer {
    protected boolean isDurableHammer;

    public ItemHammer(boolean durable) {
        isDurableHammer = false;
        isDurableHammer = durable;
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName(durable ? "durableHammer" : "hammer");
        setMaxStackSize(1);
        setMaxDamage(durable ? 1562 : 251);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = ExtraTrees.proxy.getIcon(register, isDurableHammer ? "durableHammer" : "carpentryHammer");
    }

    @Override
    public String getItemStackDisplayName(ItemStack i) {
        return isDurableHammer
                ? I18N.localise("extratrees.item.masterCarpentryHammer")
                : I18N.localise("extratrees.item.carpentryHammer");
    }

    @Override
    public boolean isActive(ItemStack stack) {
        return true;
    }

    @Override
    public void onHammerUsed(ItemStack stack, EntityPlayer player) {
        stack.damageItem(1, player);
    }
}
