package binnie.core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RecipeUtil {
	private final String modId;

	public RecipeUtil(String modId) {
		this.modId = modId;
	}

	public void addRecipe(String recipeName, Block block, Object... obj) {
		addRecipe(recipeName, new ItemStack(block), obj);
	}

	public void addRecipe(String recipeName, Item item, Object... obj) {
		addRecipe(recipeName, new ItemStack(item), obj);
	}

	public void addRecipe(String recipeName, ItemStack itemstack, Object... obj) {
		ShapedOreRecipe recipe = new ShapedOreRecipe(null, itemstack, obj);
		recipe.setRegistryName(modId, recipeName);
		ForgeRegistries.RECIPES.register(recipe);
	}

	public void addShapelessRecipe(String recipeName, ItemStack itemstack, Object... obj) {
		ShapelessOreRecipe recipe = new ShapelessOreRecipe(null, itemstack, obj);
		recipe.setRegistryName(modId, recipeName);
		ForgeRegistries.RECIPES.register(recipe);
	}
}
