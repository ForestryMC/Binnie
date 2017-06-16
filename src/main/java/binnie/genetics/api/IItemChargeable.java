package binnie.genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import forestry.api.genetics.ISpeciesRoot;

public interface IItemChargeable {
	int getCharges(ItemStack itemStack);

	int getMaxCharges(ItemStack itemStack);

	@Nullable
	ISpeciesRoot getSpeciesRoot(ItemStack itemStack);
}
