package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MultiFenceRecipeSize implements IRecipe {
	private ItemStack cached = ItemStack.EMPTY;

	@Override
	public boolean matches(final InventoryCrafting inv, final World world) {
		String pattern = "";
		final List<IPlankType> types = new ArrayList<>();
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			final ItemStack stack = inv.getStackInSlot(i);
			final IPlankType type = (stack.isEmpty()) ? null : WoodManager.get(stack);
			if (!stack.isEmpty() && type == null) {
				return false;
			}
			if (stack.isEmpty()) {
				pattern += " ";
			} else {
				if (!types.contains(type)) {
					types.add(type);
					if (types.size() > 2) {
						return false;
					}
				}
				pattern += types.indexOf(type);
			}
		}
		if (types.isEmpty()) {
			return false;
		}
		ItemStack fence = ItemStack.EMPTY;
		if (pattern.contains("0100 0   ")) {
			fence = WoodManager.getFence(types.get(0), types.get(1), new FenceType(0, false, false), 4);
		} else if (pattern.contains("0000 0   ")) {
			fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(0, false, false), 4);
		} else if (pattern.contains("0100 0 1 ")) {
			fence = WoodManager.getFence(types.get(0), types.get(1), new FenceType(1, false, false), 4);
		} else if (pattern.contains("0000 0 0 ")) {
			fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(1, false, false), 4);
		} else if (pattern.contains(" 0 1 1101")) {
			fence = WoodManager.getFence(types.get(1), types.get(0), new FenceType(2, false, false), 4);
		} else if (pattern.contains(" 0 0 0000")) {
			fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(2, false, false), 4);
		}
		this.cached = fence;
		return !fence.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inv) {
		return this.getRecipeOutput();
	}

	@Override
	public int getRecipeSize() {
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return (this.cached.isEmpty()) ? new ItemStack(Blocks.OAK_FENCE) : this.cached;
	}

	// TODO: what is this?
	public static void generateTypes() {
		final int type = 0;
		for (int type2 = 0; type2 < 2; ++type2) {
		}
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
