package binnie.extratrees.machines.lumbermill.recipes;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import binnie.core.util.OreDictionaryUtil;
import binnie.extratrees.api.recipes.ILumbermillManager;
import binnie.extratrees.api.recipes.ILumbermillRecipe;

public class LumbermillRecipeManager implements ILumbermillManager {
	private static final Multimap<Item, ILumbermillRecipe> recipes = ArrayListMultimap.create();
	private static final InventoryCrafting FAKE_CRAFT_INV = new FakeInventoryCrafting();

	public static ItemStack getPlankProduct(ItemStack logStack, World world) {
		Item logItem = logStack.getItem();
		for (ILumbermillRecipe recipe : recipes.get(logItem)) {
			if (recipe.getInput().isItemEqual(logStack)) {
				return recipe.getOutput().copy();
			}
		}

		if (OreDictionaryUtil.hasOreName(logStack, "logWood")) {
			return getRecipeWithPlanksOutput(logStack.copy(), world);
		}

		return ItemStack.EMPTY;
	}

	public static ItemStack getRecipeWithPlanksOutput(ItemStack logStack, @Nullable World world) {
		FAKE_CRAFT_INV.clear();
		FAKE_CRAFT_INV.setInventorySlotContents(0, logStack);
		for (IRecipe recipe : ForgeRegistries.RECIPES.getValuesCollection()) {
			try {
				//noinspection ConstantConditions
				if (recipe.matches(FAKE_CRAFT_INV, world)) {
					ItemStack recipeOutput = recipe.getCraftingResult(FAKE_CRAFT_INV);
					if (!recipeOutput.isEmpty()) {
						if (OreDictionaryUtil.hasOreName(recipeOutput, "plankWood")) {
							return recipeOutput;
						}
					}
				}
			} catch (RuntimeException | LinkageError ignored) {

			}
		}
		return ItemStack.EMPTY;
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

	private static class FakeInventoryCrafting extends InventoryCrafting {
		public FakeInventoryCrafting() {
			super(new FakeCraftingHandler(), 3, 3);
		}
	}
}
