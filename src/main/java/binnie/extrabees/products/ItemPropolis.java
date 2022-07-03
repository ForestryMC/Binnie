package binnie.extrabees.products;

import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemPropolis extends ItemProduct {
    public ItemPropolis() {
        super(EnumPropolis.values());
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("propolis");
    }

    @Override
    public int getColorFromItemStack(ItemStack itemStack, int j) {
        if (j == 0) {
            return EnumPropolis.get(itemStack).primaryColor;
        }
        return EnumPropolis.get(itemStack).secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = BinnieCore.proxy.getIcon(register, "forestry", "propolis.0");
    }
}
