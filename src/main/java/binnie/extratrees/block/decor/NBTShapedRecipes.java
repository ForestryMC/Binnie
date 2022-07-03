package binnie.extratrees.block.decor;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class NBTShapedRecipes implements IRecipe {
    static List<NBTShapedRecipe> recipes = new ArrayList<>();

    public static void addRecipe(NBTShapedRecipe nbtShapedRecipe) {
        NBTShapedRecipes.recipes.add(nbtShapedRecipe);
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        for (NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
            if (recipe.matches(inventory, world)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        for (NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
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
}
