package binnie.genetics.integration.jei.polymeriser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.polymeriser.PolymeriserLogic;
import mezz.jei.api.ingredients.IIngredients;

public class PolymeriserRecipeWrapper implements IRecipeWrapper {
	private final ItemStack input;

	public PolymeriserRecipeWrapper(ItemStack input) {
		this.input = input;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		inputs.add(Collections.singletonList(input));
		inputs.add(Arrays.asList(new ItemStack(Items.GOLD_NUGGET), null));
		ingredients.setInputLists(ItemStack.class, inputs);

		int processCount = input.getItemDamage();
		int dnaAmount = (int) (PolymeriserLogic.getDNAPerProcess(input) * processCount);
		int bacteriaAmount = (int) (PolymeriserLogic.getBacteriaPerProcess(input) * processCount);
		ingredients.setInputs(FluidStack.class, Arrays.asList(
			GeneticLiquid.RawDNA.get(dnaAmount),
			GeneticLiquid.BacteriaPoly.get(bacteriaAmount)
		));

		ItemStack output = input.copy();
		output.setItemDamage(0);
		ingredients.setOutput(ItemStack.class, output);
	}
}
