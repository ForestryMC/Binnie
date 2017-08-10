package binnie.extratrees.integration.crafttweaker.handlers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.api.IBinnieRecipe;
import binnie.core.api.ICraftingManager;
import binnie.core.util.ItemStackUtil;

public class GenericRecipeHandler {

	public static <R extends  IBinnieRecipe, M extends ICraftingManager<R>> void removeRecipe(ItemStack output, M manager) {
		Collection<R> recipes = manager.recipes();
		List<R> recipeToRemove = new LinkedList<>();
		for(R recipe : recipes){
			for(Object o : recipe.getOutputs()){
				if(o instanceof ItemStack){
					ItemStack itemStack = (ItemStack) o;
					if(ItemStackUtil.isItemEqual(itemStack, output, true, false)){
						recipeToRemove.add(recipe);
						break;
					}
				}
			}
		}
		CraftTweakerAPI.apply(new Remove<>(recipeToRemove, manager));
	}

	public static <R extends  IBinnieRecipe, M extends ICraftingManager<R>> void removeRecipeInput(ItemStack input, M manager) {
		Collection<R> recipes = manager.recipes();
		List<R> recipeToRemove = new LinkedList<>();
		for(R recipe : recipes){
			for(Object o : recipe.getInputs()){
				if(o instanceof ItemStack){
					ItemStack itemStack = (ItemStack) o;
					if(ItemStackUtil.isItemEqual(itemStack, input, true, false)){
						recipeToRemove.add(recipe);
						break;
					}
				}
			}
		}
		CraftTweakerAPI.apply(new Remove<>(recipeToRemove, manager));
	}

	public static <R extends  IBinnieRecipe, M extends ICraftingManager<R>> void removeRecipe(FluidStack output, M manager) {
		Collection<R> recipes = manager.recipes();
		List<R> recipeToRemove = new LinkedList<>();
		for(R recipe : recipes){
			for(Object o : recipe.getOutputs()){
				if(o instanceof FluidStack){
					FluidStack fluidStack = (FluidStack) o;
					if(fluidStack.isFluidEqual(output)){
						recipeToRemove.add(recipe);
						break;
					}
				}
			}
		}
		CraftTweakerAPI.apply(new Remove<>(recipeToRemove, manager));
	}

	public static <R extends  IBinnieRecipe, M extends ICraftingManager<R>> void removeRecipeInput(FluidStack input, M manager) {
		Collection<R> recipes = manager.recipes();
		List<R> recipeToRemove = new LinkedList<>();
		for(R recipe : recipes){
			for(Object o : recipe.getInputs()){
				if(o instanceof FluidStack){
					FluidStack fluidStack = (FluidStack) o;
					if(fluidStack.isFluidEqual(input)){
						recipeToRemove.add(recipe);
						break;
					}
				}
			}
		}
		CraftTweakerAPI.apply(new Remove<>(recipeToRemove, manager));
	}

	public static <R extends  IBinnieRecipe, M extends ICraftingManager<R>> void addRecipe(R recipe, M manager) {
		CraftTweakerAPI.apply(new Add<>(recipe, manager));
	}

	private static class Add<R extends  IBinnieRecipe, M extends ICraftingManager<R>> implements IAction {
		private final R recipe;
		private final M manager;

		public Add(R recipe, M manager) {
			this.recipe = recipe;
			this.manager = manager;
		}

		@Override
		public void apply() {
			manager.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding " + manager.toString() + " recipe for " + recipe.toString();
		}
	}

	private static class Remove<R extends  IBinnieRecipe, M extends ICraftingManager<R>> implements IAction {
		private final List<R> recipes;
		private final M manager;

		public Remove(List<R> recipes, M manager) {
			this.recipes = recipes;
			this.manager = manager;
		}

		@Override
		public void apply() {
			recipes.removeIf(recipe -> !manager.removeRecipe(recipe));
		}

		@Override
		public String describe() {
			return "Removing " + manager.toString() + " recipe for " + recipes.toString();
		}
	}
}
