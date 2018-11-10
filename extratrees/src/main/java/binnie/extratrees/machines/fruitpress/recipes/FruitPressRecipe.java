package binnie.extratrees.machines.fruitpress.recipes;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.util.FluidStackUtil;
import binnie.extratrees.api.recipes.IFruitPressRecipe;

public class FruitPressRecipe implements IFruitPressRecipe {
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

	@Override
	public Collection<Object> getInputs() {
		return Collections.singleton(input);
	}

	@Override
	public Collection<Object> getOutputs() {
		return Collections.singleton(output);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("input", input)
			.add("output", FluidStackUtil.toString(output))
			.toString();
	}
}
