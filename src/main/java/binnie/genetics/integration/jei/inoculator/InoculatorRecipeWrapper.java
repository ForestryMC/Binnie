package binnie.genetics.integration.jei.inoculator;

import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.inoculator.InoculatorLogic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public class InoculatorRecipeWrapper extends BlankRecipeWrapper {
	private static final FluidStack BACTERIA_VECTOR = GeneticLiquid.BacteriaVector.get(InoculatorLogic.BACTERIA_PER_PROCESS);

	private final ItemStack inputSerum;
	private final ItemStack wildcardTarget;

	public InoculatorRecipeWrapper(ItemStack inputSerum, ItemStack wildcardTarget) {
		this.inputSerum = inputSerum;
		this.wildcardTarget = wildcardTarget;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Arrays.asList(
				inputSerum,
				wildcardTarget
		));

		ingredients.setOutput(ItemStack.class, wildcardTarget);

		ingredients.setInput(FluidStack.class, BACTERIA_VECTOR);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

	}

	public ItemStack getInputSerum() {
		return inputSerum;
	}

	public ItemStack getWildcardTarget() {
		return wildcardTarget;
	}
}
