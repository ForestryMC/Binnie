package binnie.genetics.integration.jei.incubator;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.Incubator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class IncubatorRecipeCategory extends BlankRecipeCategory {
	@Nonnull
	private final IDrawableAnimated arrowAnimated;
	
	public IncubatorRecipeCategory() {
        IGuiHelper guiHelper = GeneticsJeiPlugin.guiHelper;
        IDrawableStatic arrowWhite = GeneticsJeiPlugin.drawables.getArrowWhite();
		this.arrowAnimated = guiHelper.createAnimatedDrawable(arrowWhite, 56, StartDirection.LEFT, false);
	}
	
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
        return GeneticsJeiPlugin.guiHelper.createBlankDrawable(94+18, 60);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
        tank.draw(minecraft, 0, 0);
        tank.draw(minecraft, 94, 0);

        IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
        arrow.draw(minecraft, 49, 26);
        arrowAnimated.draw(minecraft, 49, 26);

        IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
        slot.draw(minecraft, 22, 22);
        slot.draw(minecraft, 72, 22);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(Incubator.tankInput, true, 1, 1, 16, 58, 50, false, tankOverlay);
        fluidStacks.init(Incubator.tankOutput, false, 95, 1, 16, 58, 50, false, tankOverlay);
        fluidStacks.set(ingredients);

        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 22, 22);
        itemStacks.init(1, false, 72, 22);
        itemStacks.set(ingredients);
    }
}
