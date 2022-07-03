package binnie.extratrees.api;

import net.minecraft.item.ItemStack;

public interface IDesignMaterial {
    ItemStack getStack();

    String getName();

    int getColor();
}
