package binnie.extratrees.machines.distillery.recipes;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Collections;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.util.FluidStackUtil;
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
	public Collection<Object> getInputs() {
		return Collections.singleton(input);
	}

	@Override
	public Collection<Object> getOutputs() {
		return Collections.singleton(output);
	}

	@Override
	public int getLevel() {
		return level;
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("input", FluidStackUtil.toString(input))
			.add("output", FluidStackUtil.toString(output))
			.add("level", level)
			.toString();
	}
}
