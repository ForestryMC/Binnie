package binnie.core.integration.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SimpleRecipeHandler<T extends IRecipeWrapper> implements IRecipeHandler<T> {
	private final Class<T> recipeClass;
	private final String recipeCategoryUid;

	public SimpleRecipeHandler(Class<T> recipeClass, String recipeCategoryUid) {
		this.recipeClass = recipeClass;
		this.recipeCategoryUid = recipeCategoryUid;
	}

	@Override
	public Class<T> getRecipeClass() {
		return recipeClass;
	}

	@Override
	public String getRecipeCategoryUid() {
		return recipeCategoryUid;
	}

	@Override
	public String getRecipeCategoryUid(T recipe) {
		return recipeCategoryUid;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(T recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(T recipe) {
		return true;
	}
}
