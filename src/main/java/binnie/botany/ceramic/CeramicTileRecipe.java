package binnie.botany.ceramic;

import binnie.botany.Botany;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CeramicTileRecipe implements IRecipe {
    protected ItemStack cached;

    @Override
    public boolean matches(InventoryCrafting crafting, World world) {
        cached = getCraftingResult(crafting);
        return cached != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Item ceramicBlock = Item.getItemFromBlock(Botany.ceramic);
        Item ceramicTile = Item.getItemFromBlock(Botany.ceramicTile);
        Item ceramicBrick = Item.getItemFromBlock(Botany.ceramicBrick);
        Item mortar = Botany.misc;
        List<ItemStack> stacks = new ArrayList<>();
        int ix = -1;
        int iy = -1;

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                int x = i / 3;
                int y = i % 3;
                if (ix == -1) {
                    ix = x;
                    iy = y;
                }
                if (x - ix >= 2 || y - iy >= 2 || y < iy || x < ix) {
                    return null;
                }

                Item item = stack.getItem();
                if (item != ceramicBlock && item != ceramicTile && item != ceramicBrick && item != mortar) {
                    return null;
                }
                stacks.add(stack);
            }
        }

        for (BlockCeramicBrick.TileType type : BlockCeramicBrick.TileType.values()) {
            ItemStack stack2 = type.getRecipe(stacks);
            if (stack2 != null) {
                return stack2;
            }
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return cached;
    }
}
