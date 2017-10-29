package binnie.extratrees.integration.crafttweaker.handlers;

import net.minecraft.item.ItemStack;

import binnie.extratrees.api.recipes.ExtraTreesRecipeManager;
import binnie.extratrees.integration.crafttweaker.CraftTweakerUtil;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.extratrees.Lumbermill")
public class LumbermillRecipeHandler extends GenericRecipeHandler {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output){
		ItemStack inputStack = CraftTweakerUtil.getItemStack(input);
		ItemStack outputStack = CraftTweakerUtil.getItemStack(output);
		addRecipe(new LumbermillRecipe(inputStack, outputStack), ExtraTreesRecipeManager.lumbermillManager);
	}

	@ZenMethod
	public static void remove(IIngredient ingredient){
		if(ingredient instanceof IItemStack){
			removeRecipe(CraftTweakerUtil.getItemStack(ingredient), ExtraTreesRecipeManager.lumbermillManager);
		}
	}

	@ZenMethod
	public static void removeInput(IIngredient ingredient){
		if(ingredient instanceof IItemStack){
			removeRecipeInput(CraftTweakerUtil.getItemStack(ingredient), ExtraTreesRecipeManager.lumbermillManager);
		}
	}
}
