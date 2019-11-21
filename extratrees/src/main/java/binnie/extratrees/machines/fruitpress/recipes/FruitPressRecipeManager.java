package binnie.extratrees.machines.fruitpress.recipes;

import binnie.extratrees.api.recipes.IFruitPressManager;
import binnie.extratrees.api.recipes.IFruitPressRecipe;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collection;

public class FruitPressRecipeManager implements IFruitPressManager {
	//Map<input fruit item, Pair<input fruit, output fluid>>
	private static final Multimap<Item, IFruitPressRecipe> recipes = ArrayListMultimap.create();

	public static boolean isInput(final ItemStack itemstack) {
		return getOutput(itemstack) != null;
	}

	@Nullable
	public static FluidStack getOutput(final ItemStack itemstack) {
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

	public void addRecipe(final ItemStack stack, final FluidStack fluid) {
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
