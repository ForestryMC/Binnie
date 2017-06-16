package binnie.botany.ceramic;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlower;

public class PigmentRecipe implements IRecipe {
	@Override
	public boolean matches(final InventoryCrafting crafting, final World world) {
		return !this.getCraftingResult(crafting).isEmpty();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting crafting) {
		int n = 0;
		ItemStack stack = ItemStack.EMPTY;
		for (int i = 0; i < crafting.getSizeInventory(); ++i) {
			ItemStack stackInSlot = crafting.getStackInSlot(i);
			if (!stackInSlot.isEmpty()) {
				if (++n > 1) {
					return ItemStack.EMPTY;
				}
				if (Binnie.GENETICS.getFlowerRoot().isMember(stackInSlot)) {
					final IFlower flower = Binnie.GENETICS.getFlowerRoot().getMember(stackInSlot);
					if (flower != null && flower.getAge() >= 1) {
						stack = new ItemStack(Botany.pigment, 1, flower.getGenome().getPrimaryColor().getID());
					}
				}
			}
		}
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
