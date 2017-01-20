package binnie.genetics.integration.jei.genepool;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.genepool.Genepool;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;

public class GenepoolRecipeCategory extends BlankRecipeCategory<GenepoolRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public GenepoolRecipeCategory() {
		this.arrowAnimated = GeneticsJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.GENEPOOL;
	}

	@Override
	public String getTitle() {
		return "Genepool";
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(114, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
		tank.draw(minecraft, 0, 0);
		tank.draw(minecraft, 95, 0);

		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 69, 45);
		arrowAnimated.draw(minecraft, 69, 45);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		slot.draw(minecraft, 22, 0);
		slot.draw(minecraft, 42, 41);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, GenepoolRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Genepool.TANK_DNA, false, 96, 1, 16, 58, 100, false, tankOverlay);
		fluidStacks.init(Genepool.TANK_ETHANOL, true, 1, 1, 16, 58, 100, false, tankOverlay);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 22, 0);
		itemStacks.init(1, true, 42, 41);
		itemStacks.set(ingredients);
	}
}
