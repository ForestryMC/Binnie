package binnie.extratrees.integration.jei.distillery.brewery;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.Point;
import binnie.extratrees.machines.craftgui.ControlDistilleryProgress;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DistilleryRecipeWrapper extends BlankRecipeWrapper {
	private final int level;
	private final FluidStack input;
	private final FluidStack output;

	public DistilleryRecipeWrapper(int level, FluidStack input, FluidStack output) {
		this.level = level;
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

		CraftGUI.render.texture(ControlDistilleryProgress.OUTPUT, new Point(47, 14 + level * 15));
	}
}
