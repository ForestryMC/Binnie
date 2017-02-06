package binnie.botany.ceramic;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

public class PigmentRecipe implements IRecipe {
	@Override
	public boolean matches(final InventoryCrafting crafting, final World world) {
		return this.getCraftingResult(crafting) != null;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting crafting) {
		int n = 0;
		ItemStack stack = null;
		for (int i = 0; i < crafting.getSizeInventory(); ++i) {
			if (crafting.getStackInSlot(i) != null) {
				if (++n > 1) {
					return null;
				}
				if (Binnie.GENETICS.getFlowerRoot().isMember(crafting.getStackInSlot(i))) {
					final IFlower flower = Binnie.GENETICS.getFlowerRoot().getMember(crafting.getStackInSlot(i));
					if (flower.getAge() >= 1) {
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
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
