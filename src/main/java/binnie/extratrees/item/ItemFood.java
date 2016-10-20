package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFood extends net.minecraft.item.ItemFood {
    IItemMisc[] items;

    public ItemFood() {
        super(0, 0.0f, false);
        this.setUnlocalizedName("food");
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setHasSubtypes(true);
        this.items = Food.values();
        setRegistryName("food");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (final IItemMisc item : this.items) {
            if (item.isActive()) {
                par3List.add(this.getStack(item, 1));
            }
        }
    }

    private IItemMisc getItem(final int damage) {
        return (damage >= this.items.length) ? this.items[0] : this.items[damage];
    }

    public ItemStack getStack(final IItemMisc type, final int size) {
        return new ItemStack(this, size, type.ordinal());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        final IItemMisc item = this.getItem(par1ItemStack.getItemDamage());
        if (item != null) {
            item.addInformation(par3List);
        }
    }

    @Override
    public String getItemStackDisplayName(final ItemStack stack) {
        final IItemMisc item = this.getItem(stack.getItemDamage());
        return (item != null) ? item.getName(stack) : "null";
    }

//	@Override
//	public IIcon getIcon(final ItemStack stack, final int pass) {
//		final IItemMisc item = this.getItem(stack.getItemDamage());
//		return (item != null) ? item.getIcon(stack) : null;
//	}
//
//	@Override
//	public IIcon getIconFromDamage(final int damage) {
//		final IItemMisc item = this.getItem(damage);
//		return (item != null) ? item.getIcon(null) : null;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		for (final IItemMisc item : this.items) {
//			if (item.isActive()) {
//				item.registerIcons(register);
//			}
//		}
//	}

    private Food getFood(final ItemStack par1ItemStack) {
        return Food.values()[par1ItemStack.getItemDamage()];
    }

//	@Override
//	public int func_150905_g(final ItemStack p_150905_1_) {
//		return this.getFood(p_150905_1_).getHealth();
//	}
//
//	@Override
//	public float func_150906_h(final ItemStack p_150906_1_) {
//		return 3.0f;
//	}
}
