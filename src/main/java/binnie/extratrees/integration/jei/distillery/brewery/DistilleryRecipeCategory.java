package binnie.extratrees.integration.jei.distillery.brewery;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.extratrees.dictionary.ControlDistilleryProgress;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.integration.jei.ExtraTreesJeiPlugin;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.machines.brewery.BreweryMachine;
import binnie.extratrees.machines.distillery.DistilleryLogic;
import binnie.extratrees.machines.distillery.DistilleryMachine;
import forestry.core.tiles.ITitled;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;

public class DistilleryRecipeCategory extends BlankRecipeCategory<DistilleryRecipeWrapper> {
	private static final Texture DISTILLERY_BASE = new StandardTexture(43, 0, 58, 60, ExtraTreeTexture.Gui);
	private static final Texture LIQUID_FLOW = new StandardTexture(101, 0, 38, 60, ExtraTreeTexture.Gui);

	private final ITickTimer progress;

	public DistilleryRecipeCategory() {
		this.progress = ExtraTreesJeiPlugin.guiHelper.createTickTimer(200, 100, false);
	}

	@Override
	public String getUid() {
		return RecipeUids.DISTILLING;
	}

	@Override
	public String getTitle() {
		return "Distilling";
	}

	@Override
	public IDrawable getBackground() {
		return ExtraTreesJeiPlugin.guiHelper.createBlankDrawable(84, 60);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		CraftGUI.render.texture(DISTILLERY_BASE, new IPoint(0.0f, 0.0f));
		CraftGUI.render.texturePercentage(LIQUID_FLOW, new IArea(18.0f, 0.0f, 38.0f, 60.0f), Position.Left, progress.getValue() / 100f);

		IDrawable tank = ExtraTreesJeiPlugin.drawables.getTank();
		tank.draw(minecraft, 0, 0);
		tank.draw(minecraft, 64, 0);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistilleryRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tankOverlay = ExtraTreesJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(DistilleryMachine.TANK_INPUT, true, 1, 1, 16, 58, DistilleryLogic.INPUT_FLUID_AMOUNT, false, tankOverlay);
		fluidStacks.init(DistilleryMachine.TANK_OUTPUT, false, 65, 1, 16, 58, DistilleryLogic.INPUT_FLUID_AMOUNT, false, tankOverlay);
		fluidStacks.set(ingredients);
	}
}
