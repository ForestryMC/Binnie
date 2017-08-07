package binnie.extratrees.machines.lumbermill.recipes;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Collection;
import java.util.Collections;

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
			.add("output", output)
			.toString();
	}
}
