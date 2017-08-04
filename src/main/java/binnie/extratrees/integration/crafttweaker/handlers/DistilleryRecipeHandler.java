package binnie.extratrees.integration.crafttweaker.handlers;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.integration.crafttweaker.CraftTweakerUtil;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipe;
import minetweaker.api.item.IIngredient;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extratrees.Distillery")
public class DistilleryRecipeHandler extends GenericRecipeHandler {

	@ZenMethod
	public static void addRecipe(ILiquidStack input, ILiquidStack output, int level){
		FluidStack inputStack = CraftTweakerUtil.getLiquidStack(input);
		FluidStack outputStack = CraftTweakerUtil.getLiquidStack(output);
		addRecipe(new DistilleryRecipe(inputStack, outputStack, level), ExtraTreesRecipeManager.distilleryManager);
	}

	@ZenMethod
	public static void remove(IIngredient ingredient){
		removeRecipe(CraftTweakerUtil.getLiquidStack(ingredient), ExtraTreesRecipeManager.distilleryManager);
	}

	@ZenMethod
	public static void removeInput(IIngredient ingredient){
		removeRecipeInput(CraftTweakerUtil.getLiquidStack(ingredient), ExtraTreesRecipeManager.distilleryManager);
	}
}
