package binnie.extratrees.machines.fruitpress.recipes;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.extratrees.api.recipes.IFruitPressManager;
import binnie.extratrees.api.recipes.IFruitPressRecipe;

public class FruitPressRecipeManager implements IFruitPressManager {
	//Map<input fruit item, Pair<input fruit, output fluid>>
	private static final Multimap<Item, IFruitPressRecipe> recipes = ArrayListMultimap.create();

	public static boolean isInput(ItemStack itemstack) {
		return getOutput(itemstack) != null;
	}

	@Nullable
	public static FluidStack getOutput(ItemStack itemstack) {
		if (itemstack.isEmpty()) {
			return null;
		}

		Item item = itemstack.getItem();
		for (IFruitPressRecipe recipe : FruitPressRecipeManager.recipes.get(item)) {
			if (itemstack.isItemEqual(recipe.getInput())) {
				return recipe.getOutput();
			}
		}
		return null;
	}

	public void addRecipe(ItemStack stack, FluidStack fluid) {
		if (getOutput(stack) != null) {
			return;
		}
		Item item = stack.getItem();
		FruitPressRecipeManager.recipes.put(item, new FruitPressRecipe(stack, fluid));
	}

	@Override
	public boolean addRecipe(IFruitPressRecipe recipe) {
		Item item = recipe.getInput().getItem();
		return recipes.put(item, recipe);
	}

	@Override
	public boolean removeRecipe(IFruitPressRecipe recipe) {
		Item item = recipe.getInput().getItem();
		return recipes.get(item).remove(recipe);
	}

	@Override
	public Collection<IFruitPressRecipe> recipes() {
		return recipes.values();
	}
}
