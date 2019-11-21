package binnie.genetics.integration.jei.incubator;

import binnie.genetics.machine.incubator.IIncubatorRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;

public class IncubatorRecipeWrapper implements IRecipeWrapper {
	private final IIncubatorRecipe recipe;

	public IncubatorRecipeWrapper(IIncubatorRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, recipe.getInput());
		FluidStack fluidOutput = recipe.getOutput();
		if (fluidOutput != null) {
			ingredients.setOutput(FluidStack.class, fluidOutput);
		}

		ingredients.setInput(ItemStack.class, recipe.getInputStack());
		ItemStack output = recipe.getExpectedOutput();
		if (!output.isEmpty()) {
			ingredients.setOutput(ItemStack.class, output);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float loss = recipe.getLossChance();
		if (loss > 0) {
			minecraft.fontRenderer.drawString("Item", 24, 0, Color.gray.getRGB());
			String lossString = String.format("Loss: %.0f%%", loss * 100);
			minecraft.fontRenderer.drawString(lossString, 24, 10, Color.gray.getRGB());
		}
	}
}
