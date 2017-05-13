package binnie.extratrees.integration.jei.lumbermill;


import binnie.extratrees.integration.jei.ExtraTreesJeiPlugin;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;

public class LumbermillRecipeCategory extends BlankRecipeCategory<LumbermillRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public LumbermillRecipeCategory() {
		this.arrowAnimated = ExtraTreesJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.LUMBERMILL;
	}

	@Override
	public String getTitle() {
		return "Lumbermill";
	}

	@Override
	public IDrawable getBackground() {
		return ExtraTreesJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable arrow = ExtraTreesJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 69, 25);
		arrowAnimated.draw(minecraft, 69, 25);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, LumbermillRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = ExtraTreesJeiPlugin.drawables.getTankOverlay();
		IDrawable tank = ExtraTreesJeiPlugin.drawables.getTank();

		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(LumbermillMachine.TANK_WATER, true, 1, 1, 16, 58, LumbermillMachine.TANK_WATER_CAPACITY, false, tankOverlay);
		fluidStacks.setBackground(LumbermillMachine.TANK_WATER, tank);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(0, true, 42, 21);

		itemStacks.init(1, false, 112, 0);
		itemStacks.init(2, false, 92, 21);
		itemStacks.init(3, false, 112, 42);
		itemStacks.set(ingredients);

		IDrawable slot = ExtraTreesJeiPlugin.guiHelper.getSlotDrawable();
		for (int i = 0; i <= 3; i++) {
			itemStacks.setBackground(i, slot);
		}
	}
}
