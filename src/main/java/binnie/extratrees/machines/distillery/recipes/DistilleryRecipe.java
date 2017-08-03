package binnie.extratrees.machines.distillery.recipes;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.IDistilleryRecipe;

public class DistilleryRecipe implements IDistilleryRecipe {
	private final FluidStack input;
	private final FluidStack output;
	private final int level;

	public DistilleryRecipe(FluidStack input, FluidStack output, int level) {
		this.input = input;
		this.output = output;
		this.level = level;
	}

	@Override
	public FluidStack getOutput() {
		return output;
	}

	@Override
	public FluidStack getInput() {
		return input;
	}

	@Override
	public int getLevel() {
		return level;
	}
}
