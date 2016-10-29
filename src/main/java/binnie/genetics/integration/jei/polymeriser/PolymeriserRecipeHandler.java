package binnie.genetics.integration.jei.polymeriser;

import binnie.genetics.integration.jei.RecipeUids;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PolymeriserRecipeHandler implements IRecipeHandler<PolymeriserRecipeWrapper> {
	@Override
	public Class<PolymeriserRecipeWrapper> getRecipeClass() {
		return PolymeriserRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeUids.POLYMERISER;
	}

	@Override
	public String getRecipeCategoryUid(PolymeriserRecipeWrapper recipe) {
		return RecipeUids.POLYMERISER;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PolymeriserRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(PolymeriserRecipeWrapper recipe) {
		return true;
	}
}
