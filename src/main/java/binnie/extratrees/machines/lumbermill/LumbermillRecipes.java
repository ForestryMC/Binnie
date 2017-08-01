package binnie.extratrees.machines.lumbermill;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.core.util.FakeCraftingWorld;
import binnie.core.util.OreDictionaryUtil;

public class LumbermillRecipes {
	//Map<input log item, Pair<input log, output planks>>
	private static Multimap<Item, Pair<ItemStack, ItemStack>> recipes = ArrayListMultimap.create();

	public static ItemStack getPlankProduct(final ItemStack logStack) {
		if (recipes.isEmpty()) {
			calculateLumbermillProducts();
		}

		Item logItem = logStack.getItem();
		for (final Pair<ItemStack, ItemStack> entry : recipes.get(logItem)) {
			if (entry.getKey().isItemEqual(logStack)) {
				return entry.getValue().copy();
			}
		}
		return ItemStack.EMPTY;
	}

	public static Collection<Pair<ItemStack, ItemStack>> getRecipes() {
		if (recipes.isEmpty()) {
			calculateLumbermillProducts();
		}

		return recipes.values();
	}

	private static void calculateLumbermillProducts() {
		int plankOreId = OreDictionary.getOreID("plankWood");

		CraftingManager craftingManager = CraftingManager.getInstance();
		InventoryCrafting fakeCraftingInventory = new InventoryCrafting(new FakeCraftingHandler(), 3, 3);

		List<IWoodType> registeredWoodTypes = TreeManager.woodAccess.getRegisteredWoodTypes();
		for (IWoodType woodType : registeredWoodTypes) {
			for(boolean fireproof : new boolean[]{true, false}) {
				ItemStack logStack = TreeManager.woodAccess.getStack(woodType, WoodBlockKind.LOG, fireproof);
				ItemStack logCopy = logStack.copy();
				logCopy.setCount(1);

				fakeCraftingInventory.clear();
				fakeCraftingInventory.setInventorySlotContents(0, logCopy);

				try {
					ItemStack recipeOutput = craftingManager.findMatchingRecipe(fakeCraftingInventory, FakeCraftingWorld.getInstance());
					if (!recipeOutput.isEmpty()) {
						if (OreDictionaryUtil.hasOreId(recipeOutput, plankOreId)) {
							Item logItem = logCopy.getItem();
							ItemStack outputCopy = recipeOutput.copy();
							outputCopy.setCount((int) Math.ceil(outputCopy.getCount() * 1.5f)); // turns stack of 4 up to 6
							recipes.put(logItem, Pair.of(logCopy, outputCopy));
						}
					}
				} catch (RuntimeException ignored) {

				}
			}
		}
	}

	private static class FakeCraftingHandler extends Container {
		@Override
		public void onCraftMatrixChanged(IInventory inventoryIn) {

		}

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
		}
	}
}
