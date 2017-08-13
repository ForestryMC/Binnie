package binnie.core.api.genetics;

import net.minecraft.item.ItemStack;

public interface IItemAnalysable {
	boolean isAnalysed(ItemStack stack);

	ItemStack analyse(ItemStack stack);

	@Deprecated
	float getAnalyseTimeMult(ItemStack stack);
}
