package binnie.extratrees.machines.lumbermill.recipes;

import javax.annotation.Nullable;

import binnie.core.util.UniqueItemStackSet;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import binnie.core.util.OreDictionaryUtil;
import binnie.extratrees.api.recipes.ILumbermillManager;
import binnie.extratrees.api.recipes.ILumbermillRecipe;

public class LumbermillRecipeManager implements ILumbermillManager {
	private static Multimap<Item, ILumbermillRecipe> recipes = ArrayListMultimap.create();
	private static boolean calculatedRecipes = false;

	public static ItemStack getPlankProduct(ItemStack logStack, World world) {
		if (!calculatedRecipes) {
			calculateProducts(world);
			calculatedRecipes = true;
		}

		Item logItem = logStack.getItem();
		for (ILumbermillRecipe recipe : recipes.get(logItem)) {
			if (recipe.getInput().isItemEqual(logStack)) {
				return recipe.getOutput().copy();
			}
		}
		return ItemStack.EMPTY;
	}

	private static void calculateProducts(World world) {
		InventoryCrafting fakeCraftingInventory = new InventoryCrafting(new FakeCraftingHandler(), 3, 3){};
		NonNullList<ItemStack> oreDictLogs = OreDictionary.getOres("logWood");
		UniqueItemStackSet logsSet = new UniqueItemStackSet();
		for (ItemStack logStack : oreDictLogs) {
			List<ItemStack> subtypes = getSubtypes(logStack);
			logsSet.addAll(subtypes);
		}

		for (ItemStack log : logsSet) {
			ItemStack logCopy = log.copy();
			logCopy.setCount(1);
			fakeCraftingInventory.clear();
			fakeCraftingInventory.setInventorySlotContents(0, logCopy.copy());

			ItemStack recipeOutput = getRecipeWithPlanksOutput(fakeCraftingInventory, world);
			if (recipeOutput != null) {
				addLogToPlankRecipe(logCopy, recipeOutput.copy());
			}
		}
	}

	@Nullable
	private static ItemStack getRecipeWithPlanksOutput(InventoryCrafting fakeCraftingInventory, World world) {
		for (IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {
			boolean matches;
			try {
				matches = recipe.matches(fakeCraftingInventory, world);
			} catch (RuntimeException | LinkageError e) {
				matches  = false;
			}
			if (matches) {
				ItemStack recipeOutput = recipe.getCraftingResult(fakeCraftingInventory);
				if (!recipeOutput.isEmpty()) {
					if (OreDictionaryUtil.hasOreName(recipeOutput, "plankWood")) {
						return recipeOutput;
					}
				}
			}
		}
		return null;
	}

	private static List<ItemStack> getSubtypes(ItemStack itemStack) {
		NonNullList<ItemStack> subtypes = NonNullList.create();
		if (itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE && itemStack.getHasSubtypes()) {
			Item item = itemStack.getItem();
			for (CreativeTabs creativeTab : item.getCreativeTabs()) {
				item.getSubItems(creativeTab, subtypes);
			}
		} else {
			subtypes.add(itemStack);
		}
		return subtypes;
	}

	private static void addLogToPlankRecipe(ItemStack logStack, ItemStack plankStack){
		Item logItem = logStack.getItem();
		plankStack.setCount((int) Math.ceil(plankStack.getCount() * 1.5f)); // turns stack of 4 up to 6
		recipes.put(logItem, new LumbermillRecipe(logStack, plankStack));
	}

	@Override
	public void addRecipe(ItemStack input, ItemStack output) {
		Item logItem = input.getItem();
		recipes.put(logItem, new LumbermillRecipe(input, output));
	}

	@Override
	public boolean addRecipe(ILumbermillRecipe recipe) {
		Item item = recipe.getInput().getItem();
		return recipes.put(item, recipe);
	}

	@Override
	public boolean removeRecipe(ILumbermillRecipe recipe) {
		Item item = recipe.getInput().getItem();
		return recipes.get(item).remove(recipe);
	}

	@Override
	public Collection<ILumbermillRecipe> recipes() {
		return recipes.values();
	}

	private static class FakeCraftingHandler extends Container {
		@Override
		public void onCraftMatrixChanged(IInventory inventoryIn) {
		}

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return false;
		}
	}
}
