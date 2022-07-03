package binnie.extratrees.machines.lumbermill;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Lumbermill {
    public static final int SLOT_WOOD = 0;
    public static final int SLOT_PLANKS = 1;
    public static final int SLOT_BARK = 2;
    public static final int SLOT_SAWDUST = 3;
    public static final int TANK_WATER = 0;
    public static final int RF_COST = 900;
    public static final int TIME_PERIOD = 30;
    public static final int WATER_PER_TICK = 10;

    public static Map<ItemStack, ItemStack> recipes = new HashMap<>();

    public static ItemStack getPlankProduct(ItemStack item) {
        ItemStack stack = null;
        if (Lumbermill.recipes.isEmpty()) {
            calculateLumbermillProducts();
        }

        for (Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
            if (entry.getKey().isItemEqual(item)) {
                stack = entry.getValue().copy();
                break;
            }
        }
        stack.stackSize = 6;
        return stack;
    }

    public static void calculateLumbermillProducts() {
        for (IPlankType type : WoodManager.getAllPlankTypes()) {
            for (ItemStack wood : getRecipeResult(type.getStack())) {
                Lumbermill.recipes.put(wood, type.getStack());
            }
        }
    }

    private static Collection<ItemStack> getRecipeResult(ItemStack output) {
        List<ItemStack> list = new ArrayList<>();
        for (Object recipeO : CraftingManager.getInstance().getRecipeList()) {
            if (recipeO instanceof ShapelessRecipes) {
                ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
                if (recipe.recipeItems.size() != 1 || !(recipe.recipeItems.get(0) instanceof ItemStack)) {
                    continue;
                }

                ItemStack input = (ItemStack) recipe.recipeItems.get(0);
                if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
                    list.add(input);
                }
            }

            if (recipeO instanceof ShapedRecipes) {
                ShapedRecipes recipe2 = (ShapedRecipes) recipeO;
                if (recipe2.recipeItems.length != 1) {
                    continue;
                }

                ItemStack input = recipe2.recipeItems[0];
                if (recipe2.getRecipeOutput() != null
                        && recipe2.getRecipeOutput().isItemEqual(output)) {
                    list.add(input);
                }
            }

            if (recipeO instanceof ShapelessOreRecipe) {
                ShapelessOreRecipe recipe3 = (ShapelessOreRecipe) recipeO;
                if (recipe3.getInput().size() != 1 || !(recipe3.getInput().get(0) instanceof ItemStack)) {
                    continue;
                }

                ItemStack input = (ItemStack) recipe3.getInput().get(0);
                if (recipe3.getRecipeOutput() == null
                        || !recipe3.getRecipeOutput().isItemEqual(output)) {
                    continue;
                }
                list.add(input);
            }
        }
        return list;
    }
}
