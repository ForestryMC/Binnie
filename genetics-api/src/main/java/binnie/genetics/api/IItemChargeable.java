package binnie.genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.ISpeciesRoot;

public interface IItemChargeable {
	int getCharges(ItemStack stack);

	int getMaxCharges(ItemStack stack);

	@Nullable
	ISpeciesRoot getSpeciesRoot(ItemStack stack);
}
