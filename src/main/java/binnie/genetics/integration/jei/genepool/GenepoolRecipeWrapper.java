package binnie.genetics.integration.jei.genepool;

import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.genepool.GenepoolLogic;
import forestry.core.fluids.Fluids;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GenepoolRecipeWrapper extends BlankRecipeWrapper {
	private static final ItemStack ENZYME = GeneticsItems.Enzyme.get(1);

	private final ItemStack input;
	private final FluidStack dnaOutput;
	private final FluidStack ethanolInput;

	public GenepoolRecipeWrapper(ItemStack input) {
		this.input = input;
		int dnaAmount = GenepoolLogic.getDNAAmount(input);
		this.dnaOutput = GeneticLiquid.RawDNA.get(dnaAmount);
		this.ethanolInput = Fluids.BIO_ETHANOL.getFluid(Math.round(dnaAmount * 1.2f));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> inputs = new ArrayList<>();
		inputs.add(ENZYME);
		inputs.add(input);
		ingredients.setInputs(ItemStack.class, inputs);

		ingredients.setInput(FluidStack.class, ethanolInput);
		ingredients.setOutput(FluidStack.class, dnaOutput);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float usedEnzyme = GenepoolLogic.ENZYME_PER_PROCESS;
		String usedString = String.format("%.2f", usedEnzyme);
		minecraft.fontRendererObj.drawString(usedString, 22, 20, Color.gray.getRGB());
	}
}
