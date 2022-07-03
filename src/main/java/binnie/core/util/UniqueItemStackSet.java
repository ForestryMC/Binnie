package binnie.core.util;

import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
    @Override
    public boolean add(ItemStack itemStack) {
        return itemStack != null && getExisting(itemStack) == null && itemStacks.add(itemStack.copy());
    }

    @Override
    public boolean remove(Object object) {
        if (contains(object)) {
            ItemStack existing = getExisting((ItemStack) object);
            itemStacks.remove(existing);
        }
        return false;
    }
}
