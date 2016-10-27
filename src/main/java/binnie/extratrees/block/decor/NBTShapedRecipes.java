package binnie.extratrees.block.decor;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NBTShapedRecipes implements IRecipe {
	static List<NBTShapedRecipe> recipes = new ArrayList<>();

	@Override
	public boolean matches(final InventoryCrafting inventory, final World world) {
		for (final NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
			if (recipe.matches(inventory, world)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inventory) {
		for (final NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
			if (recipe.matches(inventory, null)) {
				return recipe.getCraftingResult(inventory);
			}
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	//TODO INV
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[0];
	}

	public static void addRecipe(final NBTShapedRecipe nbtShapedRecipe) {
		NBTShapedRecipes.recipes.add(nbtShapedRecipe);
	}

}
