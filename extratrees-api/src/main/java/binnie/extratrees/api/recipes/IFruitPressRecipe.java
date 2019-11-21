package binnie.extratrees.api.recipes;

import binnie.core.api.IBinnieRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFruitPressRecipe extends IBinnieRecipe {

	ItemStack getInput();

	FluidStack getOutput();
}
