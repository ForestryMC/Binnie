package binnie.extrabees.products;

import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemHoneyDrop extends ItemProduct {
    protected IIcon icon1;
    protected IIcon icon2;

    public ItemHoneyDrop() {
        super(EnumHoneyDrop.values());
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("honeyDrop");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getColorFromItemStack(ItemStack itemStack, int j) {
        if (j == 0) {
            return EnumHoneyDrop.get(itemStack).primaryColor;
        }
        return EnumHoneyDrop.get(itemStack).secondaryColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int i, int j) {
        if (j > 0) {
            return icon1;
        }
        return icon2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon1 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.0");
        icon2 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.1");
    }
}
