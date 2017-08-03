package binnie.extratrees.machines.fruitpress.recipes;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.IFruitPressRecipe;

public class FruitPressRecipe implements IFruitPressRecipe{
	private final ItemStack input;
	private final FluidStack output;

	public FruitPressRecipe(ItemStack input, FluidStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public FluidStack getOutput() {
		return output;
	}

	@Override
	public ItemStack getInput() {
		return input;
	}
}
