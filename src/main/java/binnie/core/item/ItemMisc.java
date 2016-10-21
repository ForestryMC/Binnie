package binnie.core.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMisc extends Item {
    private IItemMisc[] items;

    public ItemMisc() {
        setRegistryName("misc");
    }

    protected ItemMisc(final CreativeTabs tab, final IItemMisc[] items2) {
        this.setCreativeTab(tab);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("misc");
        setRegistryName("misc");
        this.items = items2;
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
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        final IItemMisc item = this.getItem(stack.getItemDamage());
        if (item != null) {
            item.addInformation(tooltip);
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
//			item.registerIcons(register);
//		}
//	}
}
