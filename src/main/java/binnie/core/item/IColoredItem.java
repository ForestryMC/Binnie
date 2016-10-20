package binnie.core.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Marcin on 19.08.2016.
 */
public interface IColoredItem {
    int getColorFromItemstack(ItemStack stack, int tintIndex);
}
