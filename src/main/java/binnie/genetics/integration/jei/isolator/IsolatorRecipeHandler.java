package binnie.genetics.integration.jei.isolator;

import binnie.genetics.integration.jei.RecipeUids;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class IsolatorRecipeHandler implements IRecipeHandler<IsolatorRecipeWrapper> {
	@Override
	public Class<IsolatorRecipeWrapper> getRecipeClass() {
		return IsolatorRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RecipeUids.ISOLATOR;
	}

	@Override
	public String getRecipeCategoryUid(IsolatorRecipeWrapper recipe) {
		return RecipeUids.ISOLATOR;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IsolatorRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(IsolatorRecipeWrapper recipe) {
		return true;
	}
}
