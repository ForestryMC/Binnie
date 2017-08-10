package binnie.botany.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.Constants;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.modules.ModuleCeramic;

public class PigmentRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	public PigmentRecipe() {
		setRegistryName(new ResourceLocation(Constants.BOTANY_MOD_ID, "pigment"));
	}

	@Override
	public boolean matches(InventoryCrafting crafting, World world) {
		return !getCraftingResult(crafting).isEmpty();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting crafting) {
		int n = 0;
		ItemStack stack = ItemStack.EMPTY;
		for (int i = 0; i < crafting.getSizeInventory(); ++i) {
			ItemStack stackInSlot = crafting.getStackInSlot(i);
			if (!stackInSlot.isEmpty()) {
				if (++n > 1) {
					return ItemStack.EMPTY;
				}
				if (BotanyAPI.flowerRoot.isMember(stackInSlot)) {
					IFlower flower = BotanyAPI.flowerRoot.getMember(stackInSlot);
					if (flower != null && flower.getAge() >= 1) {
						stack = new ItemStack(ModuleCeramic.pigment, 1, flower.getGenome().getPrimaryColor().getID());
					}
				}
			}
		}
		return stack;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 1 && height >= 1;
	}
}
