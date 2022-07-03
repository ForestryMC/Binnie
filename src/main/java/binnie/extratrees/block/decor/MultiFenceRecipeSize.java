package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MultiFenceRecipeSize implements IRecipe {
    protected ItemStack cached;

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        StringBuilder pattern = new StringBuilder();
        List<IPlankType> types = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            IPlankType type = (stack == null) ? null : WoodManager.get(stack);
            if (stack != null && type == null) {
                return false;
            }

            if (stack == null) {
                pattern.append(" ");
            } else {
                if (!types.contains(type)) {
                    types.add(type);
                    if (types.size() > 2) {
                        return false;
                    }
                }
                pattern.append(types.indexOf(type));
            }
        }

        if (types.isEmpty()) {
            return false;
        }

        ItemStack fence = null;
        if (pattern.toString().contains("0100 0   ")) {
            fence = WoodManager.getFence(types.get(0), types.get(1), new FenceType(0, false, false), 4);
        } else if (pattern.toString().contains("0000 0   ")) {
            fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(0, false, false), 4);
        } else if (pattern.toString().contains("0100 0 1 ")) {
            fence = WoodManager.getFence(types.get(0), types.get(1), new FenceType(1, false, false), 4);
        } else if (pattern.toString().contains("0000 0 0 ")) {
            fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(1, false, false), 4);
        } else if (pattern.toString().contains(" 0 1 1101")) {
            fence = WoodManager.getFence(types.get(1), types.get(0), new FenceType(2, false, false), 4);
        } else if (pattern.toString().contains(" 0 0 0000")) {
            fence = WoodManager.getFence(types.get(0), types.get(0), new FenceType(2, false, false), 4);
        }

        cached = fence;
        return fence != null;
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
