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

public class MultiFenceRecipeEmbedded implements IRecipe {
	private ItemStack cached = ItemStack.EMPTY;

	@Override
	public boolean matches(final InventoryCrafting inv, final World world) {
		for (int row = 0; row < 3; ++row) {
			ItemStack a = inv.getStackInSlot(row * 3);
			ItemStack b = inv.getStackInSlot(row * 3 + 1);
			ItemStack c = inv.getStackInSlot(row * 3 + 2);
			if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty()) {
				FenceType type = WoodManager.getFenceType(a);
				FenceType typeSecond = WoodManager.getFenceType(c);
				if (type != null && typeSecond != null && type.equals(typeSecond)) {
					FenceDescription fenceDescription = WoodManager.getFenceDescription(a);
					if(fenceDescription != null){
						IPlankType descPlankType = fenceDescription.getPlankType();
						IPlankType plankType = WoodManager.getPlankType(b);
						if (descPlankType == plankType) {
							this.cached = WoodManager.getFence(descPlankType, fenceDescription.getSecondaryPlankType(), new FenceType(type.size, type.solid, true), 2);
							return true;
						}
					}
				}
			}
		}
		return false;
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

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
