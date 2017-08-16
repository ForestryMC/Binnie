package binnie.genetics.integration.jei.incubator;

import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;

import binnie.genetics.Genetics;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.incubator.Incubator;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class IncubatorRecipeCategory implements IRecipeCategory<IncubatorRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public IncubatorRecipeCategory() {
		this.arrowAnimated = GeneticsJeiPlugin.drawables.createArrowAnimated(56);
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
	public String getModName() {
		return Genetics.instance.getModId();
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(112, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 49, 26);
		arrowAnimated.draw(minecraft, 49, 26);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IncubatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Incubator.TANK_INPUT, true, 1, 1, 16, 58, 50, false, tankOverlay);
		fluidStacks.setBackground(Incubator.TANK_INPUT, tank);
		fluidStacks.init(Incubator.TANK_OUTPUT, false, 95, 1, 16, 58, 50, false, tankOverlay);
		fluidStacks.setBackground(Incubator.TANK_OUTPUT, tank);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 22, 22);
		itemStacks.init(1, false, 72, 22);
		itemStacks.set(ingredients);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		itemStacks.setBackground(0, slot);
		itemStacks.setBackground(1, slot);
	}
}
