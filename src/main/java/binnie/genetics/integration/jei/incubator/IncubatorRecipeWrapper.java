package binnie.genetics.integration.jei.incubator;

import binnie.genetics.api.IIncubatorRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class IncubatorRecipeWrapper extends BlankRecipeWrapper {
	private final IIncubatorRecipe recipe;

	public IncubatorRecipeWrapper(IIncubatorRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, recipe.getInput());
		ingredients.setOutput(FluidStack.class, recipe.getOutput());

		ingredients.setInput(ItemStack.class, recipe.getInputStack());
		ItemStack output = recipe.getExpectedOutput();
		if (output != null) {
			ingredients.setOutput(ItemStack.class, output);
		}
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float loss = recipe.getLossChance();
		if (loss > 0) {
			minecraft.fontRendererObj.drawString("Item", 24, 0, Color.gray.getRGB());
			String lossString = String.format("Loss: %.0f%%", loss * 100);
			minecraft.fontRendererObj.drawString(lossString, 24, 10, Color.gray.getRGB());
		}
	}
}
