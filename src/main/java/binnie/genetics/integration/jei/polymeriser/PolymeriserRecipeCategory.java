package binnie.genetics.integration.jei.polymeriser;

import binnie.core.integration.jei.Drawables;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.polymeriser.Polymeriser;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PolymeriserRecipeCategory extends BlankRecipeCategory<PolymeriserRecipeWrapper> {
	@Override
	public String getUid() {
		return RecipeUids.POLYMERISER;
	}

	@Override
	public String getTitle() {
		return "Polymerisation";
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		Drawables drawables = GeneticsJeiPlugin.drawables;
		IDrawable tank = drawables.getTank();
		tank.draw(minecraft, 0, 0);
		tank.draw(minecraft, 20, 0);

		IDrawable arrow = drawables.getArrow();
		arrow.draw(minecraft, 69, 25);
		IDrawableAnimated arrowAnimated = drawables.getArrowAnimated();
		arrowAnimated.draw(minecraft, 69, 25);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		slot.draw(minecraft, 42, 21);
		slot.draw(minecraft, 42, 42);

		slot.draw(minecraft, 92, 21);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PolymeriserRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 42, 21);
		itemStacks.init(1, true, 42, 42);
		itemStacks.init(2, false, 92, 21);
		itemStacks.set(ingredients);

		itemStacks.addTooltipCallback(new ITooltipCallback<ItemStack>() {
			@Override
			public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
				if (slotIndex == 1) {
					tooltip.add("5x Processing Speed");
				}
			}
		});

		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Polymeriser.TANK_BACTERIA, true, 1, 1, 16, 58, 10000, false, tankOverlay);
		fluidStacks.init(Polymeriser.TANK_DNA, true, 21, 1, 16, 58, 10000, false, tankOverlay);
		fluidStacks.set(ingredients);
	}
}
