package binnie.extratrees.integration.jei.distillery.brewery;

import binnie.extratrees.machines.distillery.DistilleryRecipes;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class DistilleryRecipeMaker {
	public static List<DistilleryRecipeWrapper> create() {
		List<DistilleryRecipeWrapper> recipes = new ArrayList<>();

		for (int level = 0; level < 3; level++) {
			for (Pair<FluidStack, FluidStack> entry : DistilleryRecipes.getRecipes(level)) {
				FluidStack input = entry.getKey();
				FluidStack output = entry.getValue();
				recipes.add(new DistilleryRecipeWrapper(level, input, output));
			}
		}

		return recipes;
	}
}
