package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MultiFenceRecipeEmbedded implements IRecipe {
    protected ItemStack cached;

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        FenceType type;
        for (int row = 0; row < 3; ++row) {
            ItemStack a = inv.getStackInSlot(row * 3);
            ItemStack b = inv.getStackInSlot(row * 3 + 1);
            ItemStack c = inv.getStackInSlot(row * 3 + 2);
            if (a == null || b == null || c == null) {
                continue;
            }

            type = WoodManager.getFenceType(a);
            FenceType type2 = WoodManager.getFenceType(c);
            if (type == null || type2 == null || !type.equals(type2)) {
                continue;
            }

            IPlankType pType = WoodManager.get(b);
            if (WoodManager.getFenceDescription(a).getPlankType() != pType) {
                continue;
            }

            cached = WoodManager.getFence(
                    WoodManager.getFenceDescription(a).getPlankType(),
                    WoodManager.getFenceDescription(a).getSecondaryPlankType(),
                    new FenceType(type.size, type.solid, true),
                    2);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return getRecipeOutput();
    }

    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return (cached == null) ? new ItemStack(Blocks.fence) : cached;
    }
}
