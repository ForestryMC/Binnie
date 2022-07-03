package binnie.botany.ceramic;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PigmentRecipe implements IRecipe {
    protected ItemStack cached;

    private ItemStack unknown;

    public PigmentRecipe() {
        unknown = null;
    }

    @Override
    public boolean matches(InventoryCrafting crafting, World world) {
        return getCraftingResult(crafting) != null;
    }

    @Override
    public ItemStack getRecipeOutput() {
        if (cached != null) {
            return cached;
        }
        return unknown;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting) {
        int n = 0;
        ItemStack stack = null;
        for (int i = 0; i < crafting.getSizeInventory(); ++i) {
            if (crafting.getStackInSlot(i) != null) {
                if (++n > 1) {
                    return null;
                }
                if (!Binnie.Genetics.getFlowerRoot().isMember(crafting.getStackInSlot(i))) {
                    continue;
                }

                IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(crafting.getStackInSlot(i));
                if (flower.getAge() >= 1) {
                    stack = new ItemStack(
                            Botany.pigment,
                            1,
                            flower.getGenome().getPrimaryColor().getID());
                }
            }
        }
        return stack;
    }

    @Override
    public int getRecipeSize() {
        return 1;
    }
}
