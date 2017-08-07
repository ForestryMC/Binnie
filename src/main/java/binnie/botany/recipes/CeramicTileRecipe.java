package binnie.botany.recipes;

import java.util.ArrayList;
import java.util.List;

import binnie.Constants;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;

import binnie.botany.ceramic.brick.CeramicBrickType;
import binnie.botany.gardening.ModuleGardening;
import net.minecraftforge.registries.IForgeRegistryEntry;
import binnie.botany.modules.ModuleCeramic;

public class CeramicTileRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe { private ItemStack cached = ItemStack.EMPTY;

	public CeramicTileRecipe() {
		setRegistryName(new ResourceLocation(Constants.BOTANY_MOD_ID, "ceramic_tile"));
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		cached = getCraftingResult(inv);
		return !cached.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		Item ceramicBlock = Item.getItemFromBlock(ModuleCeramic.ceramic);
		Item ceramicTile = Item.getItemFromBlock(ModuleCeramic.ceramicTile);
		Item ceramicBrick = Item.getItemFromBlock(ModuleCeramic.ceramicBrick);
		Item mortar = ModuleCeramic.misc;
		List<ItemStack> stacks = new ArrayList<>();
		int ix = -1;
		int iy = -1;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				int x = i / 3;
				int y = i % 3;
				if (ix == -1) {
					ix = x;
					iy = y;
				}
				if (x - ix >= 2 || y - iy >= 2 || y < iy || x < ix) {
					return ItemStack.EMPTY;
				}
				if (stack.getItem() != ceramicBlock && stack.getItem() != ceramicTile && stack.getItem() != ceramicBrick && stack.getItem() != mortar) {
					return ItemStack.EMPTY;
				}
				stacks.add(stack);
			}
		}
		for (CeramicBrickType type : CeramicBrickType.VALUES) {
			ItemStack result = type.getRecipe(stacks);
			if (!result.isEmpty()) {
				return result;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 2 && height >= 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return cached;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
