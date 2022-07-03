package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
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

public class ItemFood extends net.minecraft.item.ItemFood {
    protected IItemMisc[] items;

    public ItemFood() {
        super(0, 0.0f, false);
        setUnlocalizedName("food");
        setCreativeTab(Tabs.tabArboriculture);
        setHasSubtypes(true);
        items = Food.values();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (IItemMisc itemMisc : items) {
            if (itemMisc.isActive()) {
                list.add(getStack(itemMisc, 1));
            }
        }
    }

    private IItemMisc getItem(int damage) {
        return (damage >= items.length) ? items[0] : items[damage];
    }

    public ItemStack getStack(IItemMisc type, int size) {
        return new ItemStack(this, size, type.ordinal());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        IItemMisc item = getItem(stack.getItemDamage());
        if (item != null) {
            item.addInformation(tooltip);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IItemMisc item = getItem(stack.getItemDamage());
        return (item != null) ? item.getName(stack) : "null";
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        IItemMisc item = getItem(stack.getItemDamage());
        return (item != null) ? item.getIcon(stack) : null;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        IItemMisc item = getItem(damage);
        return (item != null) ? item.getIcon(null) : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (IItemMisc item : items) {
            if (item.isActive()) {
                item.registerIcons(register);
            }
        }
    }

    private Food getFood(ItemStack stack) {
        return Food.values()[stack.getItemDamage()];
    }

    @Override
    public int func_150905_g(ItemStack stack) {
        return getFood(stack).getHealth();
    }

    @Override
    public float func_150906_h(ItemStack stack) {
        return 3.0f;
    }
}
