package binnie.genetics.integration.jei.incubator;

import binnie.genetics.integration.jei.RecipeUids;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class LarvaeIncubatorRecipeHandler implements IRecipeHandler<LarvaeIncubatorRecipeWrapper> {
    @Override
    public Class<LarvaeIncubatorRecipeWrapper> getRecipeClass() {
        return LarvaeIncubatorRecipeWrapper.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return RecipeUids.INCUBATOR_LARVAE;
    }

    @Override
    public String getRecipeCategoryUid(LarvaeIncubatorRecipeWrapper recipe) {
        return RecipeUids.INCUBATOR_LARVAE;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(LarvaeIncubatorRecipeWrapper recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(LarvaeIncubatorRecipeWrapper recipe) {
        return true;
    }
}
