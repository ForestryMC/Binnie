package binnie.genetics.integration.jei.isolator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.fluids.Fluids;

import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.isolator.IsolatorLogic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;

public class IsolatorRecipeWrapper implements IRecipeWrapper {
	private static final FluidStack ETHANOL = Fluids.BIO_ETHANOL.getFluid(IsolatorLogic.ETHANOL_PER_PROCESS);
	private static final ItemStack EMPTY_SEQUENCE = GeneticsItems.EmptySequencer.get(1);
	private static final ItemStack ENZYME = GeneticsItems.Enzyme.get(1);

	private final ItemStack input;
	private final ItemStack output;

	public IsolatorRecipeWrapper(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> inputs = new ArrayList<>();
		inputs.add(input);
		inputs.add(EMPTY_SEQUENCE);
		inputs.add(ENZYME);
		ingredients.setInputs(ItemStack.class, inputs);

		ingredients.setOutput(ItemStack.class, output);

		ingredients.setInput(FluidStack.class, ETHANOL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float loss = IsolatorLogic.TARGET_LOSS_CHANCE;
		String lossString = String.format("Loss: %.0f%%", loss * 100);
		minecraft.fontRenderer.drawString(lossString, 42, 0, Color.gray.getRGB());
	}
}
