package binnie.extratrees.integration.jei.distillery;

import net.minecraft.client.Minecraft;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.integration.jei.ExtraTreesJeiPlugin;
import binnie.extratrees.integration.jei.RecipeUids;
import binnie.extratrees.machines.distillery.DistilleryLogic;
import binnie.extratrees.machines.distillery.DistilleryMachine;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;

public class DistilleryRecipeCategory extends BlankRecipeCategory<DistilleryRecipeWrapper> {
	private static final Texture DISTILLERY_BASE = new StandardTexture(43, 0, 58, 60, ExtraTreeTexture.GUI);
	private static final Texture LIQUID_FLOW = new StandardTexture(101, 0, 38, 60, ExtraTreeTexture.GUI);

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
	public String getModName() {
		return ExtraTrees.instance.getModID();
	}

	@Override
	public IDrawable getBackground() {
		return ExtraTreesJeiPlugin.guiHelper.createBlankDrawable(84, 60);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtras(Minecraft minecraft) {
		CraftGUI.RENDER.texture(DISTILLERY_BASE, Point.ZERO);
		CraftGUI.RENDER.texturePercentage(LIQUID_FLOW, new Area(18, 0, 38, 60), Position.LEFT, progress.getValue() / 100f);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DistilleryRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tank = ExtraTreesJeiPlugin.drawables.getTank();
		IDrawable tankOverlay = ExtraTreesJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(DistilleryMachine.TANK_INPUT, true, 1, 1, 16, 58, DistilleryLogic.INPUT_FLUID_AMOUNT, false, tankOverlay);
		fluidStacks.setBackground(DistilleryMachine.TANK_INPUT, tank);
		fluidStacks.init(DistilleryMachine.TANK_OUTPUT, false, 65, 1, 16, 58, DistilleryLogic.INPUT_FLUID_AMOUNT, false, tankOverlay);
		fluidStacks.setBackground(DistilleryMachine.TANK_OUTPUT, tank);
		fluidStacks.set(ingredients);
	}
}
