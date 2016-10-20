// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block.decor;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.inventory.InventoryCrafting;
import java.util.List;
import net.minecraft.item.crafting.IRecipe;

public class NBTShapedRecipes implements IRecipe
{
	static List<NBTShapedRecipe> recipes;

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

	public static void addRecipe(final NBTShapedRecipe nbtShapedRecipe) {
		NBTShapedRecipes.recipes.add(nbtShapedRecipe);
	}

	static {
		NBTShapedRecipes.recipes = new ArrayList<NBTShapedRecipe>();
	}
}
