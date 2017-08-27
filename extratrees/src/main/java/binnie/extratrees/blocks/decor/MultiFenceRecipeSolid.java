package binnie.extratrees.blocks.decor;

import binnie.core.Constants;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;

import binnie.extratrees.wood.WoodManager;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MultiFenceRecipeSolid extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	private ItemStack cached = ItemStack.EMPTY;

	public MultiFenceRecipeSolid() {
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "multi_fence_solid"));
	}

	@Override
	public boolean matches(final InventoryCrafting inv, final World world) {
		for (int row = 0; row < 3; ++row) {
			ItemStack a = inv.getStackInSlot(row * 3);
			ItemStack b = inv.getStackInSlot(row * 3 + 1);
			ItemStack c = inv.getStackInSlot(row * 3 + 2);
			if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty()) {
				FenceType type = WoodManager.getFenceType(a);
				FenceType type2 = WoodManager.getFenceType(b);
				FenceType type3 = WoodManager.getFenceType(c);
				if (type != null && type2 != null && type3 != null && type.equals(type2) && type.equals(type3)) {
					FenceDescription fenceDescription = WoodManager.getFenceDescription(a);
					if (fenceDescription != null) {
						FenceType fenceType = new FenceType(type.getSize(), true, type.isSolid());
						this.cached = WoodManager.getFence(fenceDescription.getPlankType(), fenceDescription.getSecondaryPlankType(), fenceType, 2);
						return true;
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
	public boolean canFit(int width, int height) {
		return width >= 3 && height >= 3;
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
