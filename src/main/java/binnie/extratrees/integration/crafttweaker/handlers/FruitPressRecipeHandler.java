package binnie.extratrees.integration.crafttweaker.handlers;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.integration.crafttweaker.CraftTweakerUtil;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipe;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extratrees.FruitPress")
public class FruitPressRecipeHandler extends GenericRecipeHandler {

	@ZenMethod
	public static void addRecipe(IItemStack input, ILiquidStack output){
		ItemStack inputStack = CraftTweakerUtil.getItemStack(input);
		FluidStack outputStack = CraftTweakerUtil.getLiquidStack(output);
		addRecipe(new FruitPressRecipe(inputStack, outputStack), ExtraTreesRecipeManager.fruitPressManager);
	}

	@ZenMethod
	public static void remove(IIngredient ingredient){
		if(ingredient instanceof ILiquidStack){
			removeRecipe(CraftTweakerUtil.getLiquidStack(ingredient), ExtraTreesRecipeManager.fruitPressManager);
		}
	}

	@ZenMethod
	public static void removeInput(IIngredient ingredient){
		if(ingredient instanceof IItemStack){
			removeRecipeInput(CraftTweakerUtil.getItemStack(ingredient), ExtraTreesRecipeManager.fruitPressManager);
		}
	}
}
