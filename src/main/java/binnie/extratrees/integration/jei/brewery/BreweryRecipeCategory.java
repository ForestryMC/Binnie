package binnie.extratrees.integration.jei.brewery;

import binnie.extratrees.integration.jei.ExtraTreesJeiPlugin;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.machines.brewery.BreweryMachine;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import java.util.List;

public class BreweryRecipeCategory extends BlankRecipeCategory<BreweryRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public BreweryRecipeCategory() {
		this.arrowAnimated = ExtraTreesJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.BREWING;
	}

	@Override
	public String getTitle() {
		return "Brewing";
	}

	@Override
	public IDrawable getBackground() {
		return ExtraTreesJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		IDrawable tank = ExtraTreesJeiPlugin.drawables.getTank();
		tank.draw(minecraft, 0, 0);
		tank.draw(minecraft, 112, 0);

		IDrawable arrow = ExtraTreesJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 90, 25);
		arrowAnimated.draw(minecraft, 90, 25);

		IDrawable slot = ExtraTreesJeiPlugin.guiHelper.getSlotDrawable();

		// ingredients (hops)
		slot.draw(minecraft, 21, 42);

		// grain
		slot.draw(minecraft, 42, 0);
		slot.draw(minecraft, 42, 21);
		slot.draw(minecraft, 42, 42);

		// yeast
		slot.draw(minecraft, 63, 42);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BreweryRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = ExtraTreesJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(BreweryMachine.TANK_INPUT, true, 1, 1, 16, 58, Fluid.BUCKET_VOLUME, false, tankOverlay);
		fluidStacks.init(BreweryMachine.TANK_OUTPUT, false, 113, 1, 16, 58, Fluid.BUCKET_VOLUME, false, tankOverlay);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		// ingredients (hops)
		itemStacks.init(0, true, 21, 42);

		// grain
		itemStacks.init(1, true, 42, 0);
		itemStacks.init(2, true, 42, 21);
		itemStacks.init(3, true, 42, 42);

		// yeast
		itemStacks.init(4, true, 63, 42);

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		itemStacks.set(0, inputs.get(0));

		itemStacks.set(1, inputs.get(1));
		itemStacks.set(2, inputs.get(1));
		itemStacks.set(3, inputs.get(1));

		itemStacks.set(4, inputs.get(2));
	}
}
