package binnie.extratrees.block.decor;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NBTShapedRecipe implements IRecipe {
    public int recipeWidth;
    public int recipeHeight;
    public ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    private boolean field_92101_f;

    public NBTShapedRecipe(ItemStack stack, Object... p_92103_2_) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (p_92103_2_[i] instanceof String[]) {
            String[] astring = (String[]) p_92103_2_[i++];
            for (String s2 : astring) {
                k++;
                j = s2.length();
                s += s2;
            }
        } else {
            while (p_92103_2_[i] instanceof String) {
                String s3 = (String) p_92103_2_[i++];
                ++k;
                j = s3.length();
                s += s3;
            }
        }

        HashMap hashmap = new HashMap();
        while (i < p_92103_2_.length) {
            Character character = (Character) p_92103_2_[i];
            ItemStack itemstack1 = null;
            if (p_92103_2_[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) p_92103_2_[i + 1]);
            } else if (p_92103_2_[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) p_92103_2_[i + 1], 1, 32767);
            } else if (p_92103_2_[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) p_92103_2_[i + 1];
            }

            hashmap.put(character, itemstack1);
            i += 2;
        }

        ItemStack[] aitemstack = new ItemStack[j * k];
        for (int i2 = 0; i2 < j * k; ++i2) {
            char c0 = s.charAt(i2);
            if (hashmap.containsKey(c0)) {
                aitemstack[i2] = ((ItemStack) hashmap.get(c0)).copy();
            } else {
                aitemstack[i2] = null;
            }
        }

        recipeWidth = j;
        recipeHeight = k;
        recipeItems = aitemstack;
        recipeOutput = stack;
        NBTShapedRecipes.addRecipe(this);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
        for (int i = 0; i <= 3 - recipeWidth; ++i) {
            for (int j = 0; j <= 3 - recipeHeight; ++j) {
                if (checkMatch(p_77569_1_, i, j, true)) {
                    return true;
                }
                if (checkMatch(p_77569_1_, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(InventoryCrafting crafting, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                int i1 = k - p_77573_2_;
                int j1 = l - p_77573_3_;
                ItemStack itemstack = null;
                if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight) {
                    if (p_77573_4_) {
                        itemstack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
                    } else {
                        itemstack = recipeItems[i1 + j1 * recipeWidth];
                    }
                }

                ItemStack itemstack2 = crafting.getStackInRowAndColumn(k, l);
                if (itemstack2 != null || itemstack != null) {
                    if (itemstack2 == null || itemstack == null) {
                        return false;
                    }
                    if (itemstack.getItem() != itemstack2.getItem()) {
                        return false;
                    }
                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack2.getItemDamage()) {
                        return false;
                    }
                    if (itemstack.hasTagCompound()
                            && itemstack2.hasTagCompound()
                            && !ItemStack.areItemStackTagsEqual(itemstack, itemstack2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting) {
        ItemStack itemstack = getRecipeOutput().copy();
        // todo unused code?
        if (field_92101_f) {
            for (int i = 0; i < crafting.getSizeInventory(); ++i) {
                ItemStack itemstack2 = crafting.getStackInSlot(i);
                if (itemstack2 != null && itemstack2.hasTagCompound()) {
                    itemstack.setTagCompound((NBTTagCompound) itemstack2.stackTagCompound.copy());
                }
            }
        }
        return itemstack;
    }

    @Override
    public int getRecipeSize() {
        return recipeWidth * recipeHeight;
    }
}
