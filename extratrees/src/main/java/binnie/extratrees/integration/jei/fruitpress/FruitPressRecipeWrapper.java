package binnie.extratrees.integration.jei.fruitpress;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredients;

public class FruitPressRecipeWrapper implements IRecipeWrapper {
	private final ItemStack input;
	private final FluidStack output;

	public FruitPressRecipeWrapper(ItemStack input, FluidStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}
}
