package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.ceramic.brick.CeramicBrickType;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CeramicTileRecipe implements IRecipe {
	@Nullable
	private ItemStack cached;

	@Override
	public boolean matches(final InventoryCrafting inv, final World world) {
		this.cached = this.getCraftingResult(inv);
		return this.cached != null;
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inv) {
		final Item ceramicBlock = Item.getItemFromBlock(Botany.ceramic);
		final Item ceramicTile = Item.getItemFromBlock(Botany.ceramicTile);
		final Item ceramicBrick = Item.getItemFromBlock(Botany.ceramicBrick);
		final Item mortar = Botany.misc;
		final List<ItemStack> stacks = new ArrayList<>();
		int ix = -1;
		int iy = -1;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			final ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				final int x = i / 3;
				final int y = i % 3;
				if (ix == -1) {
					ix = x;
					iy = y;
				}
				if (x - ix >= 2 || y - iy >= 2 || y < iy || x < ix) {
					return null;
				}
				if (stack.getItem() != ceramicBlock && stack.getItem() != ceramicTile && stack.getItem() != ceramicBrick && stack.getItem() != mortar) {
					return null;
				}
				stacks.add(stack);
			}
		}
		for (CeramicBrickType type : CeramicBrickType.VALUES) {
			ItemStack result = type.getRecipe(stacks);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.cached;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
