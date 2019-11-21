package binnie.genetics.api;

import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IItemChargeable {
	int getCharges(ItemStack stack);

	int getMaxCharges(ItemStack stack);

	@Nullable
	ISpeciesRoot getSpeciesRoot(ItemStack stack);
}
