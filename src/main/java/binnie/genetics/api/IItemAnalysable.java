package binnie.genetics.api;

import net.minecraft.item.ItemStack;

public interface IItemAnalysable {
    boolean isAnalysed(final ItemStack p0);

    ItemStack analyse(final ItemStack p0);

    @Deprecated
    float getAnalyseTimeMult(final ItemStack p0);
}
