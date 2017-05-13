package binnie.extratrees.integration.jei.fruitpress;

import binnie.extratrees.integration.jei.ExtraTreesJeiPlugin;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.machines.fruitpress.FruitPressMachine;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;

public class FruitPressRecipeCategory extends BlankRecipeCategory<FruitPressRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public FruitPressRecipeCategory() {
		this.arrowAnimated = ExtraTreesJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.FRUIT_PRESS;
	}

	@Override
	public String getTitle() {
		return "Fruit Press";
	}

	@Override
	public IDrawable getBackground() {
		return ExtraTreesJeiPlugin.guiHelper.createBlankDrawable(74, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable arrow = ExtraTreesJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 27, 26);
		arrowAnimated.draw(minecraft, 27, 26);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FruitPressRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = ExtraTreesJeiPlugin.drawables.getTankOverlay();
		IDrawable tank = ExtraTreesJeiPlugin.drawables.getTank();

		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(FruitPressMachine.TANK_OUTPUT, false, 55, 1, 16, 58, 1000, false, tankOverlay);
		fluidStacks.setBackground(FruitPressMachine.TANK_OUTPUT, tank);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 0, 22);
		itemStacks.set(ingredients);

		IDrawable slot = ExtraTreesJeiPlugin.guiHelper.getSlotDrawable();
		itemStacks.setBackground(0, slot);
	}
}
