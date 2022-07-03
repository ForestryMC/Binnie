package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemProduct extends Item {
    protected IItemEnum[] types;

    public ItemProduct(IItemEnum[] types) {
        this.types = types;
        setMaxStackSize(64);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public IItemEnum get(ItemStack stack) {
        int i = stack.getItemDamage();
        if (i >= 0 && i < types.length) {
            return types[i];
        }
        return types[0];
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return get(stack).getName(stack);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (IItemEnum type : types) {
            if (type.isActive()) {
                list.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }
}
