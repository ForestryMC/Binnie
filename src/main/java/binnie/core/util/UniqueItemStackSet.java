package binnie.core.util;

import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
    @Override
    public boolean add(final ItemStack e) {
        return e != null && this.getExisting(e) == null && this.itemStacks.add(e.copy());
    }

    @Override
    public boolean remove(final Object o) {
        if (this.contains(o)) {
            final ItemStack r = (ItemStack) o;
            final ItemStack existing = this.getExisting(r);
            this.itemStacks.remove(existing);
        }
        return false;
    }
}
