package binnie.extratrees.machines.lumbermill.recipes;

import net.minecraft.item.ItemStack;

import binnie.extratrees.api.recipes.ILumbermillRecipe;

public class LumbermillRecipe implements ILumbermillRecipe {
	private final ItemStack input;
	private final ItemStack output;

	public LumbermillRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public ItemStack getInput() {
		return input;
	}
}
