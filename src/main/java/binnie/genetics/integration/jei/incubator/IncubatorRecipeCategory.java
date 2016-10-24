package binnie.genetics.integration.jei.incubator;

import javax.annotation.Nonnull;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.Incubator;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class IncubatorRecipeCategory extends BlankRecipeCategory {
    
	@Nonnull
	private final IDrawable tank;
	@Nonnull
	private final IDrawable tankOverlay;
	@Nonnull
	private final ResourceLocation guiTank = new ResourceLocation("binniecore:textures/gui/craftgui-slots.png");
	@Nonnull
	private final ResourceLocation guiArrow = new ResourceLocation("binniecore:textures/gui/craftgui-panels.png");
	
	@Nonnull
	private final IDrawable arrow;
	@Nonnull
	private final IDrawableAnimated arrowAnimated;
	
	public IncubatorRecipeCategory() {
		this.tank = GeneticsJeiPlugin.guiHelper.createDrawable(guiTank, 8, 28, 18, 60);
		this.tankOverlay = GeneticsJeiPlugin.guiHelper.createDrawable(guiTank, 33, 29, 16, 58);
		this.arrow = GeneticsJeiPlugin.guiHelper.createDrawable(guiArrow, 191, 79, 14, 10);
		IDrawableStatic arrowWhite = GeneticsJeiPlugin.guiHelper.createDrawable(guiArrow, 207, 79, 14, 10);
		this.arrowAnimated = GeneticsJeiPlugin.guiHelper.createAnimatedDrawable(arrowWhite, 56, StartDirection.LEFT, false);
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
        return GeneticsJeiPlugin.guiHelper.createBlankDrawable(120, 60);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        tank.draw(minecraft, 16, 0);
        tank.draw(minecraft, 80, 0);
        arrow.draw(minecraft, 52, 25);
        arrowAnimated.draw(minecraft, 52, 25);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(Incubator.tankInput, true, 17, 1, 16, 58, 10000, true, tankOverlay);
        recipeLayout.getFluidStacks().init(Incubator.tankOutput, false, 81, 1, 16, 58, 10000, true, tankOverlay);
        recipeLayout.getFluidStacks().set(ingredients);
    }
}
