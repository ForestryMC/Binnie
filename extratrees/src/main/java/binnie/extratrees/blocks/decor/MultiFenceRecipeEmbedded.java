package binnie.extratrees.blocks.decor;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.Constants;
import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.wood.planks.IPlankType;

public class MultiFenceRecipeEmbedded extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	private ItemStack cached = ItemStack.EMPTY;

	public MultiFenceRecipeEmbedded() {
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "multi_fence_embedded"));
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		for (int row = 0; row < 3; ++row) {
			ItemStack a = inv.getStackInSlot(row * 3);
			ItemStack b = inv.getStackInSlot(row * 3 + 1);
			ItemStack c = inv.getStackInSlot(row * 3 + 2);
			if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty()) {
				FenceType type = WoodManager.getFenceType(a);
				FenceType typeSecond = WoodManager.getFenceType(c);
				if (type != null && type.equals(typeSecond)) {
					FenceDescription fenceDescription = WoodManager.getFenceDescription(a);
					if (fenceDescription != null) {
						IPlankType descPlankType = fenceDescription.getPlankType();
						IPlankType plankType = WoodManager.getPlankType(b);
						if (descPlankType == plankType) {
							this.cached = WoodManager.getFence(descPlankType, fenceDescription.getSecondaryPlankType(), new FenceType(type.getSize(), type.isSolid(), true), 2);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
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
