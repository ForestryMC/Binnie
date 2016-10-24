package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import binnie.core.item.IItemMisc;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Locale;

public class ItemProduct extends Item implements IItemModelRegister {
    IItemEnum[] types;

    public ItemProduct(final IItemEnum[] types) {
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.types = types;
    }

    public IItemEnum get(final ItemStack stack) {
        final int i = stack.getItemDamage();
        if (i >= 0 && i < this.types.length) {
            return this.types[i];
        }
        return this.types[0];
    }

    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return this.get(itemstack).getName(itemstack);
    }

    @Override
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final IItemEnum type : this.types) {
            if (type.isActive()) {
                itemList.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public void registerModel(Item item, IModelManager manager) {
        for (IItemEnum type : types) {
            manager.registerItemModel(item, type.ordinal(), getRegistryName().getResourcePath());
        }
    }
}
