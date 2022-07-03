package binnie.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;

public class ItemStackSet implements Set<ItemStack> {
    protected List<ItemStack> itemStacks;

    public ItemStackSet() {
        itemStacks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return itemStacks.toString();
    }

    protected ItemStack getExisting(ItemStack stack) {
        for (ItemStack existStack : itemStacks) {
            if (existStack.isItemEqual(stack)) {
                return existStack;
            }
        }
        return null;
    }

    @Override
    public boolean add(ItemStack itemStack) {
        if (itemStack != null) {
            ItemStack existing = getExisting(itemStack);
            if (existing == null) {
                return itemStacks.add(itemStack.copy());
            }
            existing.stackSize += itemStack.stackSize;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ItemStack> c) {
        boolean addedAll = true;
        for (ItemStack stack : c) {
            addedAll = add(stack) && addedAll;
        }
        return addedAll;
    }

    @Override
    public void clear() {
        itemStacks.clear();
    }

    @Override
    public boolean contains(Object o) {
        return o instanceof ItemStack && getExisting((ItemStack) o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean addedAll = true;
        for (Object object : collection) {
            addedAll = addedAll && contains(object);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return itemStacks.isEmpty();
    }

    @Override
    public Iterator<ItemStack> iterator() {
        return itemStacks.iterator();
    }

    @Override
    public boolean remove(Object object) {
        if (contains(object)) {
            ItemStack r = (ItemStack) object;
            ItemStack existing = getExisting(r);
            if (existing.stackSize > r.stackSize) {
                existing.stackSize -= r.stackSize;
            } else {
                itemStacks.remove(existing);
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean addedAll = true;
        for (Object object : collection) {
            boolean removed = remove(object);
            addedAll = removed && addedAll;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return itemStacks.retainAll(collection);
    }

    @Override
    public int size() {
        return itemStacks.size();
    }

    @Override
    public Object[] toArray() {
        return itemStacks.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return itemStacks.toArray(array);
    }
}
