package binnie.genetics.integration.jei.incubator;

import binnie.genetics.api.IIncubatorRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;

public class IncubatorRecipeWrapper extends BlankRecipeWrapper {
    private final IIncubatorRecipe recipe;

    public IncubatorRecipeWrapper(IIncubatorRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(FluidStack.class, recipe.getInput());
        ingredients.setOutput(FluidStack.class, recipe.getOutput());
    }
}
