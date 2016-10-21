package binnie.extratrees.block.decor;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class NBTShapedRecipe implements IRecipe {
    public final int recipeWidth;
    public final int recipeHeight;
    public final ItemStack[] recipeItems;
    @Nonnull
    private ItemStack recipeOutput;
    private boolean field_92101_f;
    private static final String __OBFID = "CL_00000093";

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    //TODO INV
    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return new ItemStack[0];
    }

    @Override
    public boolean matches(final InventoryCrafting p_77569_1_, final World p_77569_2_) {
        for (int i = 0; i <= 3 - this.recipeWidth; ++i) {
            for (int j = 0; j <= 3 - this.recipeHeight; ++j) {
                if (this.checkMatch(p_77569_1_, i, j, true)) {
                    return true;
                }
                if (this.checkMatch(p_77569_1_, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(final InventoryCrafting p_77573_1_, final int p_77573_2_, final int p_77573_3_, final boolean p_77573_4_) {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                final int i1 = k - p_77573_2_;
                final int j1 = l - p_77573_3_;
                ItemStack itemstack = null;
                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
                    if (p_77573_4_) {
                        itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    } else {
                        itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }
                final ItemStack itemstack2 = p_77573_1_.getStackInRowAndColumn(k, l);
                if (itemstack2 != null || itemstack != null) {
                    if ((itemstack2 == null && itemstack != null) || (itemstack2 != null && itemstack == null)) {
                        return false;
                    }
                    if (itemstack.getItem() != itemstack2.getItem()) {
                        return false;
                    }
                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack2.getItemDamage()) {
                        return false;
                    }
                    if (itemstack.hasTagCompound() && itemstack2.hasTagCompound() && !ItemStack.areItemStackTagsEqual(itemstack, itemstack2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
        final ItemStack itemstack = this.getRecipeOutput().copy();
        if (this.field_92101_f) {
            for (int i = 0; i < p_77572_1_.getSizeInventory(); ++i) {
                final ItemStack itemstack2 = p_77572_1_.getStackInSlot(i);
                if (itemstack2 != null && itemstack2.hasTagCompound()) {
                    itemstack.setTagCompound(itemstack2.getTagCompound().copy());
                }
            }
        }
        return itemstack;
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    public NBTShapedRecipe(@Nonnull final ItemStack recipeOutput, final Object... p_92103_2_) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if (p_92103_2_[i] instanceof String[]) {
            final String[] astring = (String[]) p_92103_2_[i++];
            for (final String s2 : astring) {
                ++k;
                j = s2.length();
                s += s2;
            }
        } else {
            while (p_92103_2_[i] instanceof String) {
                final String s3 = (String) p_92103_2_[i++];
                ++k;
                j = s3.length();
                s += s3;
            }
        }
        final HashMap hashmap = new HashMap();
        while (i < p_92103_2_.length) {
            final Character character = (Character) p_92103_2_[i];
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
        final ItemStack[] aitemstack = new ItemStack[j * k];
        for (int i2 = 0; i2 < j * k; ++i2) {
            final char c0 = s.charAt(i2);
            if (hashmap.containsKey(c0)) {
                aitemstack[i2] = ((ItemStack) hashmap.get(c0)).copy();
            } else {
                aitemstack[i2] = null;
            }
        }
        this.recipeWidth = j;
        this.recipeHeight = k;
        this.recipeItems = aitemstack;
        this.recipeOutput = recipeOutput;
        NBTShapedRecipes.addRecipe(this);
    }
}
