package binnie.genetics.integration.jei.incubator;

import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.IncubatorRecipeLarvae;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class IncubatorRecipeHandler implements IRecipeHandler<IIncubatorRecipe> {
	@Override
	public Class<IIncubatorRecipe> getRecipeClass() {
		return IIncubatorRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeUids.INCUBATOR;
	}

	@Override
	public String getRecipeCategoryUid(IIncubatorRecipe recipe) {
		return RecipeUids.INCUBATOR;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IIncubatorRecipe recipe) {
		return new IncubatorRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(IIncubatorRecipe recipe) {
		return !(recipe instanceof IncubatorRecipeLarvae);
	}
}
