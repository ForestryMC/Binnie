package binnie.genetics.integration.jei.polymeriser;

import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

import binnie.genetics.Genetics;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.polymeriser.Polymeriser;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class PolymeriserRecipeCategory implements IRecipeCategory<PolymeriserRecipeWrapper> {
	@Override
	public String getUid() {
		return RecipeUids.POLYMERISER;
	}

	@Override
	public String getTitle() {
		return "Polymerisation";
	}

	@Override
	public String getModName() {
		return Genetics.instance.getModID();
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 69, 25);
		IDrawableAnimated arrowAnimated = GeneticsJeiPlugin.drawables.getArrowAnimated();
		arrowAnimated.draw(minecraft, 69, 25);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PolymeriserRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 42, 21);
		itemStacks.init(1, true, 42, 42);
		itemStacks.init(2, false, 92, 21);
		itemStacks.set(ingredients);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		for (int i = 0; i <= 2; i++) {
			itemStacks.setBackground(i, slot);
		}

		itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			if (slotIndex == 1) {
				tooltip.add("5x Processing Speed");
			}
		});

		IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Polymeriser.TANK_BACTERIA, true, 1, 1, 16, 58, 10000, false, tankOverlay);
		fluidStacks.setBackground(Polymeriser.TANK_BACTERIA, tank);
		fluidStacks.init(Polymeriser.TANK_DNA, true, 21, 1, 16, 58, 10000, false, tankOverlay);
		fluidStacks.setBackground(Polymeriser.TANK_DNA, tank);
		fluidStacks.set(ingredients);
	}
}
