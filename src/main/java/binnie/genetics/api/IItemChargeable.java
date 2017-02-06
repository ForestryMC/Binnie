package binnie.genetics.api;

import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IItemChargeable {
	int getCharges(ItemStack itemStack);

	int getMaxCharges(ItemStack itemStack);

	@Nullable
	ISpeciesRoot getSpeciesRoot(ItemStack itemStack);
}
