package binnie.extratrees.integration.jei.lumbermill;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.items.ExtraTreeItems;
import binnie.extratrees.machines.lumbermill.LumbermillLogic;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class LumbermillRecipeWrapper implements IRecipeWrapper {
	private static final FluidStack WATER = new FluidStack(FluidRegistry.WATER, LumbermillLogic.WATER_PER_TICK * LumbermillLogic.PROCESS_LENGTH);

	private final ItemStack inputLog;
	private final ItemStack outputPlanks;

	public LumbermillRecipeWrapper(ItemStack inputLog, ItemStack outputPlanks) {
		this.inputLog = inputLog;
		this.outputPlanks = outputPlanks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, inputLog);
		ingredients.setInput(FluidStack.class, WATER);

		ingredients.setOutputs(ItemStack.class, Arrays.asList(
			ExtraTreeItems.Bark.get(2),
			outputPlanks,
			ExtraTreeItems.SAWDUST.get(2)
		));
	}
}
