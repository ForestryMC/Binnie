package binnie.genetics.integration.jei.isolator;

import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.incubator.Incubator;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class IsolatorRecipeCategory extends BlankRecipeCategory<IsolatorRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public IsolatorRecipeCategory() {
		this.arrowAnimated = GeneticsJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.ISOLATOR;
	}

	@Override
	public String getTitle() {
		return "Gene Isolation";
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
		tank.draw(minecraft, 0, 0);

		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 69, 25);
		arrowAnimated.draw(minecraft, 69, 25);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		slot.draw(minecraft, 22, 0);
		slot.draw(minecraft, 42, 21);
		slot.draw(minecraft, 22, 42);

		slot.draw(minecraft, 92, 21);

		String randomText = "(Random)";
		int textWidth = minecraft.fontRendererObj.getStringWidth(randomText);
		minecraft.fontRendererObj.drawString(randomText, 102 - (textWidth / 2), 45, Color.gray.getRGB());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IsolatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Incubator.TANK_INPUT, true, 1, 1, 16, 58, 100, false, tankOverlay);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 22, 0);
		itemStacks.init(1, true, 42, 21);
		itemStacks.init(2, true, 22, 42);
		itemStacks.init(3, false, 92, 21);
		itemStacks.set(ingredients);
	}
}
