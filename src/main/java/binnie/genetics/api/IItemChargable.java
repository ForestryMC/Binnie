package binnie.genetics.api;

import net.minecraft.item.ItemStack;

public interface IItemChargable {
    int getCharges(ItemStack stack);
}
