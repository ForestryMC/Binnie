package binnie.genetics.integration.jei.incubator;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.Incubator;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class IncubatorRecipeCategory extends BlankRecipeCategory {
    @Override
    public String getUid() {
        return RecipeUids.INCUBATOR;
    }

    @Override
    public String getTitle() {
        return "Incubation";
    }

    @Override
    public IDrawable getBackground() {
        return GeneticsJeiPlugin.guiHelper.createBlankDrawable(60, 60);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(Incubator.tankInput, true, 0, 0, 16, 59, 10000, true, null);
        recipeLayout.getFluidStacks().init(Incubator.tankOutput, false, 32, 0, 16, 59, 10000, true, null);
        recipeLayout.getFluidStacks().set(ingredients);
    }
}
